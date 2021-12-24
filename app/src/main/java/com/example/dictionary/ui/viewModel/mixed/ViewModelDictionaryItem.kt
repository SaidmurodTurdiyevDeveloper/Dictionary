package com.example.dictionary.ui.viewModel.mixed

import androidx.lifecycle.*
import com.example.dictionary.contracts.mixed.ContractDictionaryItem
import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.data.model.DataMoveCountry
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDictionaryItem @Inject constructor(
    private var repostory: ContractDictionaryItem.Model
) :
    ContractDictionaryItem.ViwModel, ViewModel() {

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

    private val _openCountryOneDialogLiveData = MediatorLiveData<Event<DataMoveCountry>>()
    val openCountryOneDialogLiveData: LiveData<Event<DataMoveCountry>> get() = _openCountryOneDialogLiveData

    private val _openCountryTwoDialogLiveData = MediatorLiveData<Event<DataMoveCountry>>()
    val openCountryTwoDialogLiveData: LiveData<Event<DataMoveCountry>> get() = _openCountryTwoDialogLiveData


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


    override fun openCountryDilog(isCountryOne: Boolean, id: Long) {
        viewModelScope.launch {
            val data = repostory.getData(id)
            if (isCountryOne)
                _openCountryOneDialogLiveData.postValue(Event(DataMoveCountry(data.languageIdOne)))
            else
                _openCountryTwoDialogLiveData.postValue(
                    Event(
                        DataMoveCountry(
                            data.languageIdTwo,
                            data.languageIdOne
                        )
                    )
                )
        }
    }

    override fun setCountryOne(countryId: Int, id: Long) {
        viewModelScope.launch {
            val data = repostory.getData(id)
            data.languageIdOne = countryId
            repostory.updateData(data)
            _loadCountryOneLivedata.postValue(repostory.getCountryList()[countryId])
        }
    }

    override fun setCountryTwo(countryId: Int, id: Long) {
        viewModelScope.launch {
            val data = repostory.getData(id)
            data.languageIdTwo = countryId
            repostory.updateData(data)
            _loadCountryTwoLivedata.postValue(repostory.getCountryList()[countryId])

        }
    }

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