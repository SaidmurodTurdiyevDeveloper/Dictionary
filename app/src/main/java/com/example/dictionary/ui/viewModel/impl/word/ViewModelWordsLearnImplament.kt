package com.example.dictionary.ui.viewModel.impl.word

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dictionary.data.model.DataWord
import com.example.dictionary.data.model.Event
import com.example.dictionary.domen.usecase_word.UseCaseWordsLearn
import com.example.dictionary.utils.other.ViewModelHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelWordsLearnImplament @Inject constructor(private var useCase: UseCaseWordsLearn) : ViewModelHelper() {
    private var dictionaryId = -1L
    private val _listOfWordsLiveData = MutableLiveData<List<DataWord>>(emptyList())
    val listOfWordsLiveData: LiveData<List<DataWord>> get() = _listOfWordsLiveData

    private val _openItemLiveData = MutableLiveData<Event<Long>>()
    val openItemLiveData: LiveData<Event<Long>> get() = _openItemLiveData

    private val _addDataLiveData = MutableLiveData<Event<DataWord>>()
    val addDataLiveData: LiveData<Event<DataWord>> get() = _addDataLiveData

    private val _updateDataLiveData = MutableLiveData<Event<DataWord>>()
    val updateDataLiveData: LiveData<Event<DataWord>> get() = _updateDataLiveData

    private val _deletDataLiveData = MutableLiveData<Event<DataWord>>()
    val deletDataLiveData: LiveData<Event<DataWord>> get() = _deletDataLiveData

    fun loadList(id: Long) {
        viewModelScope.launch {

            loadFlow(
                useCase.getWordsList(id), { list ->
                    _listOfWordsLiveData.postValue(list)
                    dictionaryId = id
                }
            )
        }
    }

    fun add() {
        viewModelScope.launch {
            if (dictionaryId >= 0) {
                val data = DataWord(-1, "", "", "", -1, 0, false, isActiveSecond = false)
                _addDataLiveData.postValue(Event(data) { dataWord ->
                    loadFlow(useCase.addNewData(dataWord, dictionaryId), { list ->
                        _listOfWordsLiveData.postValue(list)
                    })
                })
            }
        }
    }

    fun update(data: DataWord) {
        viewModelScope.launch {
            _updateDataLiveData.postValue(Event(data) { dataWord ->
                loadFlow(useCase.updateData(dataWord, data), { list ->
                    _listOfWordsLiveData.postValue(list)
                })
            })
        }
    }

    fun delete(data: DataWord) {
        viewModelScope.launch {
            _deletDataLiveData.postValue(Event(data) { dataWord ->
                loadFlow(useCase.deleteData(dataWord), { list ->
                    _listOfWordsLiveData.postValue(list)
                    _snackBarLiveData.postValue(Event("This ${data.wordFirst}-${data.wordSecond} deleted", "undo") {
                        loadFlow(useCase.addNewData(data, dictionaryId), { list ->
                            _listOfWordsLiveData.postValue(list)
                        })
                    })
                })
            })
        }
    }

    fun close() {
        viewModelScope.launch {
            _closeWindowLiveData.postValue(Event(Unit))
        }
    }

    fun clickItem(data: DataWord) {
        viewModelScope.launch {
            _openItemLiveData.postValue(Event(data.id))
        }
    }
}










