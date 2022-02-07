package com.example.dictionary.ui.viewModel.impl.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.contracts.dictionary.ContractMain
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.domen.usecase_dictionary.UseCaseMain
import com.example.dictionary.utils.other.Responce
import com.example.dictionary.utils.other.sendOneParametreBlock
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMain @Inject constructor(
    private var useCase: UseCaseMain
) : ContractMain.ViewModel, ViewModel() {
    private var isSelectItem = false
    private var isSelectAllItem = false

    private val _loadDictionaryListLiveData = MutableLiveData<Event<List<DictionaryEntity>>>()
    val loadDictionaryListLiveData: LiveData<Event<List<DictionaryEntity>>> get() = _loadDictionaryListLiveData

    private val _loadCountLearnWordsLiveData = MutableLiveData<Event<String>>()
    val loadCountLearnWordsLiveData: LiveData<Event<String>> get() = _loadCountLearnWordsLiveData

    private val _clickItemLiveData = MutableLiveData<Event<Long>>()
    val clickItemLiveData: LiveData<Event<Long>> get() = _clickItemLiveData

    private val _dayNightClickLiveData = MutableLiveData<Event<Boolean>>()
    val dayNighttClickLiveData: LiveData<Event<Boolean>> get() = _dayNightClickLiveData

    private val _deleteLiveData = MutableLiveData<Event<DictionaryEntity>>()
    val deleteLiveData: LiveData<Event<DictionaryEntity>> get() = _deleteLiveData

    private val _addLiveData = MutableLiveData<Event<DictionaryEntity>>()
    val addLiveData: LiveData<Event<DictionaryEntity>> get() = _addLiveData

    private val _updateLiveData = MutableLiveData<Event<DictionaryEntity>>()
    val updateLiveData: LiveData<Event<DictionaryEntity>> get() = _updateLiveData

    private val _openHomeLiveData = MutableLiveData<Event<Unit>>()
    val openHomeLiveData: LiveData<Event<Unit>> get() = _openHomeLiveData

    private val _openArxiveLiveData = MutableLiveData<Event<Unit>>()
    val openArxiveLiveData: LiveData<Event<Unit>> get() = _openArxiveLiveData

    private val _openSettingLiveData = MutableLiveData<Event<Unit>>()
    val openSettingLiveData: LiveData<Event<Unit>> get() = _openSettingLiveData

    private val _openGameLiveData = MutableLiveData<Event<Unit>>()
    val openGameLiveData: LiveData<Event<Unit>> get() = _openGameLiveData

    private val _openChangeLanguageLiveData = MutableLiveData<Event<Unit>>()
    val openChangeLanguageLiveData: LiveData<Event<Unit>> get() = _openChangeLanguageLiveData

    private val _openAppInfoLiveData = MutableLiveData<Event<Unit>>()
    val openAppInfoLiveData: LiveData<Event<Unit>> get() = _openAppInfoLiveData

    private val _openAnotherActionBarLiveData = MutableLiveData<Event<Unit>>()
    val openAnotherActionBarLiveData: LiveData<Event<Unit>> get() = _openAnotherActionBarLiveData

    private var _closeAnotherActionBarLiveData = MutableLiveData<Event<Unit>>()
    val closeAnotherActionBarLiveData: LiveData<Event<Unit>> get() = _closeAnotherActionBarLiveData

    private val _selectCheckBoxWhichSelectAll = MutableLiveData<Event<Boolean>>()
    val selectCheckBoxWhichSelectAll: LiveData<Event<Boolean>> get() = _selectCheckBoxWhichSelectAll

    private val _loadingScreenLivedata = MutableStateFlow(false)
    val loadingScreenLivedata = _loadingScreenLivedata.asStateFlow()

    private val _openDictionaryItemLiveData = MutableLiveData<Event<Long>>()
    val openDictionaryItemLiveData: LiveData<Event<Long>> get() = _openDictionaryItemLiveData

    private val _showMessageDialogLiveData = MutableLiveData<Event<String>>()
    val showMessageLiveData: LiveData<Event<String>> get() = _showMessageDialogLiveData

    private val _showToastLiveData = MutableLiveData<Event<String>>()
    val showToastLiveData: LiveData<Event<String>> get() = _showToastLiveData

    private val _showErrorLiveData = MutableLiveData<Event<String>>()
    val showErrorLiveData: LiveData<Event<String>> get() = _showErrorLiveData

    private val _showSnackbarLiveData = MutableLiveData<Event<String>>()
    val showSnackbarLiveData: LiveData<Event<String>> get() = _showSnackbarLiveData

    override fun loadData() {
        viewModelScope.launch {
            loadFlow(useCase.getDictionaryList()) { _loadDictionaryListLiveData.postValue(Event(it)) }
        }
    }

    override fun loadCountLearnedWord() {
        viewModelScope.launch {
            useCase.getCountLearnedWords().onEach {
                _loadCountLearnWordsLiveData.postValue(Event(it))
            }.catch {
                _showToastLiveData.postValue(Event("Words could not find which of learned"))
            }
        }
    }

    override fun clickItem(data: DictionaryEntity) {
        if (isSelectItem) {
            loadFlow(useCase.selectItem(data)) {
                _loadDictionaryListLiveData.postValue(Event(it))
                isSelectAllItem = useCase.selectedItemcount() == it.size
                _selectCheckBoxWhichSelectAll.postValue(Event(isSelectAllItem))
            }
        } else
            _clickItemLiveData.postValue(Event(data.id))
    }

    override fun delete(data: DictionaryEntity) {
        _deleteLiveData.postValue(Event(data) { dictionaryEntity ->
            viewModelScope.launch {
                loadFlow(useCase.removeDictionaryActive(dictionaryEntity)) {firstlist->
                    _loadDictionaryListLiveData.postValue(Event(firstlist))
                    _showSnackbarLiveData.postValue(Event("${data.name} is deleted") {
                        viewModelScope.launch {
                            loadFlow(useCase.removeItemDictionary(data)) { secondList->
                                _loadDictionaryListLiveData.postValue(Event(secondList))
                                _showToastLiveData.postValue(Event("Returned"))
                            }
                        }
                    })
                }
            }
        })
    }

    override fun dayNightClick() {
        viewModelScope.launch {
            useCase.changeDayNight().onEach { _dayNightClickLiveData.postValue(Event(it)) }.catch {
                _showToastLiveData.postValue(Event("Day night is wrong"))
            }.launchIn(viewModelScope)
        }
    }

    override fun add() {
        _addLiveData.postValue(Event(DictionaryEntity(-1, "Empty", "None", 0, 0, 0, 0))
        { data ->
            viewModelScope.launch {
                loadFlow(useCase.addDictionary(data)) { list ->
                    _loadDictionaryListLiveData.postValue(Event(list))
                    _showToastLiveData.postValue(Event("New Item Added"))
                }
            }
        })
    }

    override fun update(oldData: DictionaryEntity) {
        _updateLiveData.postValue(Event(oldData) { data ->
            viewModelScope.launch {
                loadFlow(useCase.updateDictionary(oldData, data)) { list ->
                    _loadDictionaryListLiveData.postValue(Event(list))
                    _showToastLiveData.postValue(Event("Update"))
                }
            }
        })
    }

    override fun openHome() {
        _openHomeLiveData.value = Event(Unit)
    }

    override fun openArxive() {
        _openArxiveLiveData.value = Event(Unit)
    }

    override fun openSetting() {
        _openSettingLiveData.value = Event(Unit)
    }

    override fun openGame() {
        _openGameLiveData.value = Event(Unit)
    }

    override fun openChangeLanguage() {
        _openChangeLanguageLiveData.value = Event(Unit)
    }

    override fun openAppInfo() {
        _openAppInfoLiveData.value = Event(Unit)
    }

    override fun deleteAll() {
        val items = useCase.selectedItems()
        _showMessageDialogLiveData.postValue(Event("${items.size} items can be deleted") {
            viewModelScope.launch {
                loadFlow(useCase.deleteSelectedList()) { list ->
                    _loadDictionaryListLiveData.postValue(Event(list))
                    _closeAnotherActionBarLiveData.postValue(Event(Unit))
                    _showSnackbarLiveData.postValue(Event("${items.size} items deleted") {
                        viewModelScope.launch {
                            loadFlow(useCase.removeListDictionary(items)) {
                                _loadDictionaryListLiveData.postValue(Event(it))
                                _showToastLiveData.postValue(Event("Returned"))
                            }
                        }
                    })
                }
            }
        })
    }

    override fun checkAll() {
        isSelectAllItem = !isSelectAllItem
        loadFlow(useCase.selectAllItem(isSelectAllItem)) {
            _loadDictionaryListLiveData.postValue(Event(it))
            _selectCheckBoxWhichSelectAll.postValue(Event(isSelectAllItem))
        }
    }

    override fun openDictionaryItem(id: Long) = _openDictionaryItemLiveData.postValue(Event(id))

    override fun cancelSelected() {
        isSelectItem = false
        useCase.cancelSelect()
        _closeAnotherActionBarLiveData.postValue(Event(Unit))
        loadData()
    }

    override fun openAnotherActionBar() {
        isSelectItem = true
        _openAnotherActionBarLiveData.postValue(Event(Unit))
    }

    private fun <T> loadFlow(flow: Flow<Responce<T>>, succeslistener: sendOneParametreBlock<T>) {
        flow.onEach {
            when (it) {
                is Responce.Error -> {
                    _loadingScreenLivedata.value=false
                    _loadDictionaryListLiveData.postValue(Event(emptyList()))
                    _showSnackbarLiveData.postValue(Event(it.error){
                        loadData()
                    })
                }
                is Responce.Loading -> {
                    _loadingScreenLivedata.value=it.cond
                }
                is Responce.Message -> {
                    _loadingScreenLivedata.value=false
                    _showToastLiveData.postValue(Event(it.message))
                }
                is Responce.Success -> {
                    _loadingScreenLivedata.value=false
                    succeslistener.invoke(it.data)
                }
            }
        }.catch {
            _loadingScreenLivedata.value=false
            _loadDictionaryListLiveData.postValue(Event(emptyList()))
            _showErrorLiveData.postValue(Event("Try again"))
            _showToastLiveData.postValue(Event("Wrong!"))
        }.launchIn(viewModelScope)
    }
}