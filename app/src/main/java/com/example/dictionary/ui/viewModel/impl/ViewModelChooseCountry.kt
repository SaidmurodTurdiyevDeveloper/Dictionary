package com.example.dictionary.ui.viewModel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.contracts.ContractChooseLanguages
import com.example.dictionary.data.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelChooseCountry @Inject constructor(
    private var model: ContractChooseLanguages.Model
) : ContractChooseLanguages.ViewModel, ViewModel() {

    private val _loadCountryOneLiveData = MediatorLiveData<Event<Int>>()
    val loadCountryOneLiveData: LiveData<Event<Int>> get() = _loadCountryOneLiveData

    private val _loadCountryTwoLiveData = MediatorLiveData<Event<Int>>()
    val loadCountryTwoLiveData: LiveData<Event<Int>> get() = _loadCountryTwoLiveData

    private val _openNextLiveData = MediatorLiveData<Event<Unit>>()
    val openNextLiveData: LiveData<Event<Unit>> get() = _openNextLiveData

    private val _closeLiveData = MediatorLiveData<Event<Unit>>()
    val closeLiveData: LiveData<Event<Unit>> get() = _closeLiveData

    init {
            viewModelScope.launch {
                _loadCountryOneLiveData.postValue(Event(model.getFirstCountryId()))
                _loadCountryTwoLiveData.postValue(Event(model.getSecondCountryId()))
            }
    }

    override fun done() {
        viewModelScope.launch {
            if (model.getThisFirstTimeEnter())
                _closeLiveData.postValue(Event(Unit))
            else {
                model.setFirstTimeEnter()
                _openNextLiveData.postValue(Event(Unit))
            }
        }
    }

    override fun clickFirstCountry(countryId: Int) {
        viewModelScope.launch {
            if (model.getSecondCountryId() == countryId) {
                val t1 = model.getFirstCountryId()
                model.setSecondCountryId(t1)
                _loadCountryTwoLiveData.postValue(Event(t1))
            }
            model.setFirstCountryId(countryId)
        }
    }

    override fun clickSecondCountry(countryId: Int) {
        viewModelScope.launch {
            if (model.getFirstCountryId() != countryId) {
                model.setSecondCountryId(countryId)
                _loadCountryTwoLiveData.postValue(Event(countryId))
            }
            else{
                _loadCountryTwoLiveData.postValue(Event(model.getSecondCountryId()))
            }
        }
    }
}