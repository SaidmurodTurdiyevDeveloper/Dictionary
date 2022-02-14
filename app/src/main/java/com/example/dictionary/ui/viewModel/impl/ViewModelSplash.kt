package com.example.dictionary.ui.viewModel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.contracts.ContractSplash
import com.example.dictionary.data.model.Event
import com.example.dictionary.domen.UseCaseSplash
import com.example.dictionary.utils.other.Responce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelSplash @Inject constructor(private var useCase: UseCaseSplash) : ViewModel(), ContractSplash.ViewModel {

    private val _openChooselanguageLiveData = MutableLiveData<Unit>()
    override val openChooselanguageLiveData: LiveData<Unit> get() = _openChooselanguageLiveData

    private val _loadingScreenLiveData = MutableLiveData<Event<Boolean>>()
    override val loadingScreenLiveData: LiveData<Event<Boolean>> get() = _loadingScreenLiveData

    private val _openMainScreenLiveData = MutableLiveData<Unit>()
    override val openMainScreenLiveData: LiveData<Unit> get() = _openMainScreenLiveData

    private val _showMessage = MutableLiveData<Event<String>>()
    override val showMessage: LiveData<Event<String>> get() = _showMessage

    override fun init() {
        viewModelScope.launch {
            useCase.init().collectLatest {
                when (it) {
                    is Responce.Message -> {
                        _showMessage.postValue(Event(it.message))
                    }
                    is Responce.Loading -> {
                        _loadingScreenLiveData.postValue(Event(it.cond))
                    }
                    is Responce.Error -> {
                        _showMessage.postValue(Event(it.error))
                    }
                    is Responce.Success -> {
                        if (it.data) {
                            _openMainScreenLiveData.postValue(Unit)
                        } else {
                            _openChooselanguageLiveData.postValue(Unit)
                        }
                    }
                }
            }
        }
    }
}