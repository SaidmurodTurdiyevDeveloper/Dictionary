package com.example.dictionary.ui.viewModel.impl.word

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dictionary.data.model.DataWord
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.model.changeisActive
import com.example.dictionary.data.model.replase
import com.example.dictionary.domen.usecase_word.UseCaseWordList
import com.example.dictionary.utils.other.ViewModelHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelWordListImplament @Inject constructor(private var useCase: UseCaseWordList) : ViewModelHelper() {
    private var list = ArrayList<DataWord>()

    private val _listOfWordsLiveData = MutableLiveData<List<DataWord>>()
    val listOfWordsLiveData: LiveData<List<DataWord>> = _listOfWordsLiveData

    private val _openItemInfoFlow = MutableLiveData<Event<Long>>()
    val openItemInfoFlow: LiveData<Event<Long>> = _openItemInfoFlow

    fun loadList(id: Long) = loadList(id, isActiveFirst = true, isActiveSecond = false)

    fun close() {
        viewModelScope.launch {
            _closeWindowLiveData.postValue(Event(Unit))
        }
    }

    fun clickItem(data: DataWord) {
        viewModelScope.launch {
            try {
                list.replase(data.changeisActive())
                _listOfWordsLiveData.postValue(list.toMutableList())
            } catch (e: Exception) {
                _toastLiveData.postValue(Event("Wrong"))
            }
        }
    }

    fun openItem(data: DataWord) {
        viewModelScope.launch {
            _openItemInfoFlow.postValue(Event(data.id))
        }
    }

    fun openAll(id: Long) = loadList(id, isActiveFirst = true, isActiveSecond = true)

    fun firstToSecond(id: Long) = loadList(id, isActiveFirst = true, isActiveSecond = false)

    fun secondtoFirst(id: Long) = loadList(id, isActiveFirst = false, isActiveSecond = true)

    private fun loadList(id: Long, isActiveFirst: Boolean, isActiveSecond: Boolean) {
        viewModelScope.launch {
            loadFlow(useCase.getWordList(id, isActiveFirst, isActiveSecond), { ls ->
                _listOfWordsLiveData.postValue(ls)
                list.clear()
                list.addAll(ls)
            }, Event("It could not load, please try again", "reload") {
                loadList(id)
            }, true)
        }
    }
}