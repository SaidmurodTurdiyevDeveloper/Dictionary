package com.example.dictionary.ui.viewModel.impl.word

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dictionary.data.model.DataWord
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.domen.usecase_word.UseCaseWordsPlay
import com.example.dictionary.utils.other.ViewModelHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelWordsPlayImplament @Inject constructor(private var useCase: UseCaseWordsPlay) : ViewModelHelper() {
    private var list = ArrayList<DataWord>()
    private var currentDataPosition = 0
    var currentData: DataWord = DataWord(-1, "None", "None", "empty", -1, 0, isActiveFirst = false, isActiveSecond = false)
        private set
    private var isFirst = false
    private var currentDictionary = DictionaryEntity(-1, "", "", 0, -1, -1, 0)

    var firstWordList: List<Char> = ArrayList()
        private set
    var secondWordList: List<Char> = ArrayList()
        private set
    private var currentText: String = ""

    private val _currentTextLiveData = MutableLiveData<Event<String>>()
    val currentTextLiveData: LiveData<Event<String>> = _currentTextLiveData

    private val _titleLiveData = MutableLiveData<String>()
    val titleLiveData: LiveData<String> = _titleLiveData

    private val _isChangedDataLiveData = MutableLiveData<Boolean>()
    val isChangedDataLiveData: LiveData<Boolean> = _isChangedDataLiveData

    fun loadWordsList(id: Long) {
        viewModelScope.launch {
            loadFlow(useCase.getList(id), { ls ->
                list.clear()
                list.addAll(ls)
                if (list.isNotEmpty()) {
                    currentText = ""
                    currentData = list.first()
                    firstWordList = currentData.wordFirst.toList()
                    secondWordList = currentData.wordSecond.toList()
                    _currentTextLiveData.postValue(Event(currentText))
                    _isChangedDataLiveData.postValue(isFirst)
                    currentDataPosition = 0
                }
            })
            loadFlow(useCase.getDictionary(id), {
                currentDictionary = it
                _titleLiveData.value = it.name
            })
        }
    }

    fun done(text: String) {
        loadFlow(useCase.done(currentData, text, currentDictionary.id), { ls ->
            viewModelScope.launch {
                list.clear()
                list.addAll(ls)
                delay(200)
                if (list.size > ++currentDataPosition) {
                    currentText = ""
                    currentData = list[currentDataPosition]
                    firstWordList = currentData.wordFirst.toList()
                    secondWordList = currentData.wordSecond.toList()
                    _currentTextLiveData.postValue(Event(currentText))
                    _isChangedDataLiveData.postValue(isFirst)
                } else {
                    if (currentData.dictionaryId != -1L) {
                        currentDataPosition = list.size - 1
                        currentData = list.last()
                        firstWordList = currentData.wordFirst.toList()
                        secondWordList = currentData.wordSecond.toList()
                        _currentTextLiveData.postValue(Event(currentText))
                        _isChangedDataLiveData.postValue(isFirst)
                        _snackBarLiveData.postValue(Event("Finish", "Reload") {
                            loadWordsList(currentData.dictionaryId)
                        })
                    }
                }
            }
        }, Event("Something Wrong, please try again", "Reload") {
            _isChangedDataLiveData.postValue(isFirst)
        }, true)
    }

    fun next() {
        viewModelScope.launch {
            if (list.size > ++currentDataPosition) {
                currentText = ""
                currentData = list[currentDataPosition]
                firstWordList = currentData.wordFirst.toList()
                secondWordList = currentData.wordSecond.toList()
                _isChangedDataLiveData.postValue(isFirst)
            } else {
                if (currentData.dictionaryId != -1L) {
                    currentDataPosition = list.size - 1
                    currentData = list.last()
                    firstWordList = currentData.wordFirst.toList()
                    secondWordList = currentData.wordSecond.toList()
                    _currentTextLiveData.postValue(Event(currentText))
                    _isChangedDataLiveData.postValue(isFirst)
                    _toastLiveData.postValue(Event("Finish"))
                }
            }
        }
    }

    fun previous() {
        viewModelScope.launch {
            if (0 <= --currentDataPosition) {
                currentText = ""
                currentData = list[currentDataPosition]
                firstWordList = currentData.wordFirst.toList()
                secondWordList = currentData.wordSecond.toList()
                _currentTextLiveData.postValue(Event(currentText))
                _isChangedDataLiveData.postValue(isFirst)
            } else {
                if (list.isNotEmpty()) {
                    currentDataPosition = 0
                    currentData = list[currentDataPosition]
                    firstWordList = currentData.wordFirst.toList()
                    secondWordList = currentData.wordSecond.toList()
                    _currentTextLiveData.postValue(Event(currentText))
                    _isChangedDataLiveData.postValue(isFirst)
                    _toastLiveData.postValue(Event("Restart"))
                }
            }
        }
    }

    fun close() {
        viewModelScope.launch {
            _closeWindowLiveData.postValue(Event(Unit))
        }
    }

    fun check(cond: Boolean) {
        viewModelScope.launch {
            currentText = ""
            _currentTextLiveData.postValue(Event(currentText))
            isFirst = cond
            _isChangedDataLiveData.postValue(isFirst)
        }
    }

    fun clickKeyboard(text: String) {
        viewModelScope.launch {
            currentText += text
            _currentTextLiveData.postValue(Event(currentText))
        }
    }
}