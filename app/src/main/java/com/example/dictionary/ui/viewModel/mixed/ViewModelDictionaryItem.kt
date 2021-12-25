package com.example.dictionary.ui.viewModel.mixed

import androidx.lifecycle.*
import com.example.dictionary.contracts.mixed.ContractDictionaryItem
import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDictionaryItem @Inject constructor(private var repostory: ContractDictionaryItem.Model) : ContractDictionaryItem.ViwModel, ViewModel() {

    private val _closeLivedata = MutableLiveData<Unit>()
    val closeLiveData: LiveData<Unit> get() = _closeLivedata

    private val _loadLivedata = MediatorLiveData<DictionaryEntity>()
    val loadLivedata: LiveData<DictionaryEntity> get() = _loadLivedata

    private val _loadCountryOneLivedata = MediatorLiveData<DataCountry>()
    val loadCountryOneLivedata: LiveData<DataCountry> get() = _loadCountryOneLivedata

    private val _loadCountryTwoLivedata = MediatorLiveData<DataCountry>()
    val loadCountryTwoLivedata: LiveData<DataCountry> get() = _loadCountryTwoLivedata

    private val _loadLearnPrecentLivedata = MediatorLiveData<String>()
    val loadLearnPrecentLivedata: LiveData<String> get() = _loadLearnPrecentLivedata

    private val _openInfoLiveData = MediatorLiveData<Event<String>>()
    val openInfoLiveData: LiveData<Event<String>> get() = _openInfoLiveData

    private val _openListLiveData = MediatorLiveData<Event<Unit>>()
    val openListLiveData: LiveData<Event<Unit>> get() = _openListLiveData

    override fun openInfo(id: Long) {
        viewModelScope.launch {
            _openInfoLiveData.postValue(
                Event(
                    repostory.getTextInfo(id)
                )
            )
        }
    }
    override fun openList() = _openListLiveData.postValue(Event(Unit))


    override fun loadItem(id: Long) {
        viewModelScope.launch {
            val data = repostory.getData(id)
            _loadLivedata.postValue(data)
            _loadCountryOneLivedata.postValue(repostory.getCountryList()[data.languageIdOne])
            _loadCountryTwoLivedata.postValue(repostory.getCountryList()[data.languageIdTwo])
            val text = repostory.getLearnCountText() + "\n${data.learnPracent}%"
            _loadLearnPrecentLivedata.postValue(text)
        }
    }
    override fun close() = _closeLivedata.postValue(Unit)
}