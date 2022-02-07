package com.example.dictionary.ui.viewModel.impl.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.contracts.dictionary.ContractArchive
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.domen.usecase_dictionary.UseCaseArchive
import com.example.dictionary.utils.other.Responce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelArchive @Inject constructor(private var useCase: UseCaseArchive) :
    ContractArchive.ViewModel, ViewModel() {

    private val _loadAllDataLiveData = MutableLiveData<Event<List<DictionaryEntity>>>()
    val loadAllDataLiveData: LiveData<Event<List<DictionaryEntity>>> get() = _loadAllDataLiveData

    private val _itemTouchLiveData = MutableLiveData<Event<DictionaryEntity>>()
    val itemTouchLiveData: LiveData<Event<DictionaryEntity>> get() = _itemTouchLiveData

    private val _backLiveData = MutableLiveData<Event<Unit>>()
    val backLiveData: LiveData<Event<Unit>> get() = _backLiveData

    private val _showMessageDialogLiveData = MutableLiveData<Event<String>>()
    val showMessageDialogLiveData: LiveData<Event<String>> get() = _showMessageDialogLiveData

    private val _showErrorMessegeLiveData = MutableLiveData<Event<String>>()
    val showErrorMessegeLiveData: LiveData<Event<String>> get() = _showErrorMessegeLiveData

    private val _showSnackBarLiveData = MutableLiveData<Event<String>>()
    val showSnackBarLiveData: LiveData<Event<String>> get() = _showSnackBarLiveData

    private val _showToastLiveData = MutableLiveData<Event<String>>()
    val showToastLiveData: LiveData<Event<String>> get() = _showToastLiveData

    private val _loadingLiveData = MutableStateFlow(false)
    val loadingLiveData: StateFlow<Boolean> = _loadingLiveData.asStateFlow()

    override fun itemTouch(data: DictionaryEntity) = _itemTouchLiveData.postValue(Event(data))

    override fun delete(data: DictionaryEntity) {
        loadFlow(useCase.delete(data)) {
            _loadAllDataLiveData.postValue(Event(it))
        }
    }

    override fun returnToActive(data: DictionaryEntity) {
        loadFlow(useCase.update(data)) {
            _loadAllDataLiveData.postValue(Event(it))
        }
    }

    override fun clearAll() {
        viewModelScope.launch {
            useCase.getSize().collect { size ->
                _showMessageDialogLiveData.postValue(Event("$size is can be delete") {
                    loadFlow(useCase.deleteAll()) { list ->
                        _loadAllDataLiveData.postValue(Event(list))
                    }
                })
            }
        }
    }

    override fun back() = _backLiveData.postValue(Event(Unit))

    override fun getArchiveList() {
        loadFlow(useCase.getAllArxiveList()) {
            _loadAllDataLiveData.postValue(Event(it))
        }
    }

    private fun <T> loadFlow(flow: Flow<Responce<T>>, successListener: (T) -> Unit) {
        viewModelScope.launch {
            flow.collectLatest {
                try {
                    when (it) {
                        is Responce.Message -> {
                            _loadingLiveData.value = false
                            _showErrorMessegeLiveData.postValue(Event(it.message))
                        }
                        is Responce.Success -> {
                            _loadingLiveData.value = false
                            successListener.invoke(it.data)
                        }
                        is Responce.Error -> {
                            _loadingLiveData.value = false
                            _showSnackBarLiveData.postValue(Event(it.error) {
                                getArchiveList()
                            })
                        }
                        is Responce.Loading -> {
                            _loadingLiveData.value = it.cond
                        }
                    }
                } catch (e: Exception) {
                    _loadingLiveData.value = false
                    _showToastLiveData.postValue(Event(e.message.toString()))
                }
            }
        }

    }
}