package com.example.dictionary.ui.viewModel.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.contracts.dictionary.ContractChooseLanguages
import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.data.model.DataMoveCountry
import com.example.dictionary.data.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelChooseCountry @Inject constructor(
    private var model: ContractChooseLanguages.Model
) : ContractChooseLanguages.ViewModel, ViewModel() {

    private val _loadCountryOneLiveData = MediatorLiveData<Event<DataCountry>>()
    val loadCountryOneLiveData: LiveData<Event<DataCountry>> get() = _loadCountryOneLiveData

    private val _loadCountryTwoLiveData = MediatorLiveData<Event<DataCountry>>()
    val loadCountryTwoLiveData: LiveData<Event<DataCountry>> get() = _loadCountryTwoLiveData

    private val _openNextLiveData = MediatorLiveData<Event<Unit>>()
    val openNextLiveData: LiveData<Event<Unit>> get() = _openNextLiveData

    private val _closeLiveData = MediatorLiveData<Event<Unit>>()
    val closeLiveData: LiveData<Event<Unit>> get() = _closeLiveData
    private val _openCountryDialogOneLiveData = MediatorLiveData<Event<DataMoveCountry>>()
    val openCountryDialogOneLiveData: LiveData<Event<DataMoveCountry>> get() = _openCountryDialogOneLiveData

    private val _openCountryDialogTwoLiveData = MediatorLiveData<Event<DataMoveCountry>>()
    val openCountryDialogTwoLiveData: LiveData<Event<DataMoveCountry>> get() = _openCountryDialogTwoLiveData

    init {
            viewModelScope.launch {

            val countryOne = model.getCountryOne()
            if (countryOne > 0) {
                _loadCountryOneLiveData.postValue(Event(model.getCountryList()[countryOne]))
            } else {
                model.setCountryOne(0)
                _loadCountryOneLiveData.postValue(Event(model.getCountryList()[0]))
            }
            }
         viewModelScope.launch {
            val countryTwo = model.getCountryTwo()
            if (countryTwo > 0) {
                _loadCountryTwoLiveData.postValue(Event(model.getCountryList()[countryTwo]))
            } else {
                model.setCountryTwo(1)
                _loadCountryTwoLiveData.postValue(Event(model.getCountryList()[1]))
            }
        }
    }

    override fun setCauntryOne(countryId: Int) {
         viewModelScope.launch {
            if (model.getCountryTwo() == countryId) {
                val t1 = model.getCountryOne()
                model.setCountryTwo(t1)
                _loadCountryTwoLiveData.postValue(Event(model.getCountryList()[t1]))
            }
            model.setCountryOne(countryId)
            _loadCountryOneLiveData.postValue(Event(model.getCountryList()[countryId]))
        }
    }

    override fun setCauntryTwo(countryId: Int) {
         viewModelScope.launch {
            if (model.getCountryOne() != countryId) {
                model.setCountryTwo(countryId)
                _loadCountryTwoLiveData.postValue(Event(model.getCountryList()[model.getCountryTwo()]))
            }
        }
    }

    override fun done() {
         viewModelScope.launch {
            if (model.getIsFirstCountry())
                _closeLiveData.postValue(Event(Unit))
            else {
                model.setFirstEnter()
                _openNextLiveData.postValue(Event(Unit))
            }
        }
    }

    override fun clickOneCountry() {
         viewModelScope.launch {
            _openCountryDialogOneLiveData.postValue(Event(DataMoveCountry(model.getCountryOne())))
        }
    }

    override fun clickTwoCountry() {
         viewModelScope.launch {
            _openCountryDialogTwoLiveData.postValue(
                Event(
                    DataMoveCountry(
                        model.getCountryTwo(),
                        model.getCountryOne()
                    )
                )
            )
        }
    }


}