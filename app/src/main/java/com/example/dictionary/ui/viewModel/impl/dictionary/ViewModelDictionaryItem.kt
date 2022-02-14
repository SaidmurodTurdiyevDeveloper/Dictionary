package com.example.dictionary.ui.viewModel.impl.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.contracts.dictionary.ContractDictionaryItem
import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.domen.usecase_dictionary.UseCaseItemDictionary
import com.example.dictionary.utils.other.Responce
import com.example.dictionary.utils.other.sendOneParametreBlock
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDictionaryItem @Inject constructor(private var useCase: UseCaseItemDictionary) : ContractDictionaryItem.ViwModel, ViewModel() {

    private var dictionaryId: Long = -1
    private lateinit var dictionary: DictionaryEntity

    private val _closeLivedata = MutableLiveData<Unit>()
    val closeLiveData: LiveData<Unit> get() = _closeLivedata

    private val _loadLivedata = MutableLiveData<DictionaryEntity>()
    val loadLivedata: LiveData<DictionaryEntity> get() = _loadLivedata

    private val _loadCountryOneLivedata = MutableLiveData<DataCountry>()
    val loadCountryOneLivedata: LiveData<DataCountry> get() = _loadCountryOneLivedata

    private val _loadCountryTwoLivedata = MutableLiveData<DataCountry>()
    val loadCountryTwoLivedata: LiveData<DataCountry> get() = _loadCountryTwoLivedata

    private val _loadLearnPrecentLivedata = MutableLiveData<String>()
    val loadLearnPrecentLivedata: LiveData<String> get() = _loadLearnPrecentLivedata

    private val _openListLiveData = MutableLiveData<Event<Unit>>()
    val openListLiveData: LiveData<Event<Unit>> get() = _openListLiveData

    private val _openInfoLiveData = MutableLiveData<Event<Long>>()
    val openInfoLiveData: LiveData<Event<Long>> get() = _openInfoLiveData

    private val _showToastLiveData = MutableLiveData<Event<String>>()
    val showToastLiveData: LiveData<Event<String>> get() = _showToastLiveData

    private val _showMessageLiveData = MutableLiveData<Event<String>>()
    val showMessageLiveData: LiveData<Event<String>> get() = _showMessageLiveData

    private val _loadingScreenLivedata = MutableStateFlow(false)
    val loadingScreenLivedata = _loadingScreenLivedata.asStateFlow()

    private val _showSnackbarLiveData = MutableLiveData<Event<String>>()
    val showSnackbarLiveData: LiveData<Event<String>> get() = _showSnackbarLiveData

    override fun openList() = _openListLiveData.postValue(Event(Unit))

    override fun openInfo(id: Long) = _openInfoLiveData.postValue(Event(id))

    override fun loadItem(id: Long) {
        dictionaryId = id
        loadFlow(useCase.getDictionary(id)) {
            dictionary = it
            loadFlow(useCase.getDictionaryLearnCount(id)) { s ->
                _loadLearnPrecentLivedata.postValue(s)
            }
            viewModelScope.launch {
                val countryFirst = useCase.getCountryWithId(it.languageIdOne)
                val countrySecond = useCase.getCountryWithId(it.languageIdTwo)
                _loadCountryOneLivedata.postValue(countryFirst)
                _loadCountryTwoLivedata.postValue(countrySecond)
            }
        }
    }

    override fun close() = _closeLivedata.postValue(Unit)

    private fun <T> loadFlow(flow: Flow<Responce<T>>, succeslistener: sendOneParametreBlock<T>) {
        flow.onEach {
            when (it) {
                is Responce.Error -> {
                    _loadingScreenLivedata.value = false
                    _showSnackbarLiveData.postValue(Event(it.error) {
                        loadItem(dictionaryId)
                    })
                }
                is Responce.Loading -> {
                    _loadingScreenLivedata.value = it.cond
                }
                is Responce.Message -> {
                    _loadingScreenLivedata.value = false
                    _showMessageLiveData.postValue(Event(it.message))
                }
                is Responce.Success -> {
                    _loadingScreenLivedata.value = false
                    succeslistener.invoke(it.data)
                }
            }
        }.catch {
            _loadingScreenLivedata.value = false
            _loadLivedata.postValue(DictionaryEntity(-1, "None", "Wrong", 0, 0, 0, 0))
            _showToastLiveData.postValue(Event("Wrong!"))
        }.launchIn(viewModelScope)
    }
}