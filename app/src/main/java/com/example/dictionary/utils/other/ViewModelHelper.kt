package com.example.dictionary.utils.other

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.data.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

open class ViewModelHelper : ViewModel() {
    protected val _toastLiveData = MutableLiveData<Event<String>>()
    val toastLiveData: LiveData<Event<String>> = _toastLiveData

    protected val _messageLiveData = MutableLiveData<Event<String>>()
    val messageLiveData: LiveData<Event<String>> = _messageLiveData

    protected val _errorLivedata = MutableLiveData<Event<String>>()
    val errorLiveData: LiveData<Event<String>> = _errorLivedata

    protected val _snackBarLiveData = MutableLiveData<Event<String>>()
    val snackBarLiveData: LiveData<Event<String>> = _snackBarLiveData

    protected val _loadingLayoutLiveData = MutableLiveData<Boolean>()
    val loadingLayoutLiveData: LiveData<Boolean> = _loadingLayoutLiveData

    protected val _closeWindowLiveData = MutableLiveData<Event<Unit>>()
    val closeWindowLiveData: LiveData<Event<Unit>> = _closeWindowLiveData
    private var event = Event("Wrong")
    protected open fun <T> loadFlow(flow: Flow<Responce<T>>, succeslistener: sendOneParametreBlock<T>, errorSnackBarEvent: Event<String>? = null, isActiveSnackBarListner: Boolean = false) {
        viewModelScope.launch {
            flow.collectLatest {
                when (it) {
                    is Responce.Error -> {
                        _errorLivedata.postValue(Event(it.error))
                        if (isActiveSnackBarListner && errorSnackBarEvent != null) {
                            _snackBarLiveData.postValue(errorSnackBarEvent ?: event)
                        }
                        _loadingLayoutLiveData.postValue(false)
                    }
                    is Responce.Loading -> _loadingLayoutLiveData.postValue(it.cond)
                    is Responce.Message -> {
                        _messageLiveData.postValue(Event(it.message))
                        _loadingLayoutLiveData.postValue(false)
                    }
                    is Responce.Success -> {
                        succeslistener.invoke(it.data)
                        _loadingLayoutLiveData.postValue(false)
                    }
                }
            }
        }
    }
}