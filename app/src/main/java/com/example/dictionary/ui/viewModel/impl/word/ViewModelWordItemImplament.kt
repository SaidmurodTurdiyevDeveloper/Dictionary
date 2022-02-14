package com.example.dictionary.ui.viewModel.impl.word

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.WordEntity
import com.example.dictionary.domen.usecase_word.UseCaseWordItem
import com.example.dictionary.utils.other.ViewModelHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelWordItemImplament @Inject constructor(private var useCase: UseCaseWordItem) : ViewModelHelper() {
    private var wordId: Long = -1
    private lateinit var word: WordEntity

    private val _wordLiveData = MutableLiveData<WordEntity>()
    val wordLiveData: LiveData<WordEntity> = _wordLiveData

    private val _saveOrCloseLiveData = MutableLiveData<Event<String>>()
    val saveOrCloseLiveData: LiveData<Event<String>> = _saveOrCloseLiveData

    private val _examplesTextChangedLiveData = MutableLiveData<Boolean>()
    val examplesTextChangedLiveData: LiveData<Boolean> = _examplesTextChangedLiveData

    fun loadData(id: Long) {
        viewModelScope.launch {
            wordId = id
            loadFlow(useCase.getWord(id), {
                word = it
                _wordLiveData.postValue(it)
            }, Event("It could not load, please try again", "Reload") {
                loadData(id)
            }, true)
        }
    }

    fun done(text: String) = viewModelScope.launch {
        loadFlow(useCase.update(data = word, text), {
            word = it
            _wordLiveData.postValue(it)
        }, Event("Please try again", "Reload") {
            loadData(wordId)
        }, true)
    }

    fun examplesTextChanged(text: String) = viewModelScope.launch {
        try {
            _examplesTextChangedLiveData.postValue(word.example == text)
        } catch (e: Exception) {
            viewModelScope.launch {
                _snackBarLiveData.postValue(Event(e.message.toString()) {
                    loadData(wordId)
                })
            }
        }
    }

    fun close(text: String) = viewModelScope.launch {
        try {
            if (word.example == text)
                _closeWindowLiveData.postValue(Event(Unit))
            else
                _saveOrCloseLiveData.postValue(Event("Do you want save your exmples?") {
                    viewModelScope.launch {
                        done(text)
                        _closeWindowLiveData.postValue(Event(Unit))
                    }
                })
        } catch (e: Exception) {
            _snackBarLiveData.postValue(Event(e.message.toString()) {
                loadData(wordId)
            })
        }
    }
}