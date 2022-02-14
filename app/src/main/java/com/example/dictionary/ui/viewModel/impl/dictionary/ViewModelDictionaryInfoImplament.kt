package com.example.dictionary.ui.viewModel.impl.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.domen.usecase_dictionary.UseCaseDictionaryInfo
import com.example.dictionary.utils.other.ViewModelHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDictionaryInfoImplament @Inject constructor(private var useCase: UseCaseDictionaryInfo) : ViewModelHelper() {

    private lateinit var dictionary: DictionaryEntity
    private var dictionaryId: Long = -1

    private val _dictionaryLiveData = MutableLiveData<DictionaryEntity>()
    val dictionaryLiveData: LiveData<DictionaryEntity> = _dictionaryLiveData

    private val _saveOrDoesNotSaveDataLiveData = MutableLiveData<Event<String>>()
    val saveOrDoesNotSaveDataLiveData: LiveData<Event<String>> = _saveOrDoesNotSaveDataLiveData

    fun loadData(id: Long) {
        dictionaryId = id
        loadFlow(useCase.getDictionary(id), {
            dictionary = it
            _dictionaryLiveData.postValue(dictionary)
        }, Event("It could not load, please try again", "Reload") {
            loadData(id)
        }, true)
    }

    fun done(text: String) {
        loadFlow(useCase.update(dictionary, text), {
            dictionary = it
            _dictionaryLiveData.postValue(dictionary)
            viewModelScope.launch {
                delay(300)
                _closeWindowLiveData.postValue(Event(Unit))
            }
        })
    }

    fun close(text: String) {
        viewModelScope.launch {
            if (dictionary.dataInfo == text)
                _closeWindowLiveData.postValue(Event(Unit))
            else
                _saveOrDoesNotSaveDataLiveData.postValue(Event(dictionary.name))
        }
    }
}