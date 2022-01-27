package com.example.dictionary.ui.viewModel.dictionary.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.contracts.dictionary.ContractArchive
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.domen.dictionary.UseCaseArchive
import com.example.dictionary.utils.other.Responce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelArxive @Inject constructor(private var useCase: UseCaseArchive) :
    ContractArchive.ViewModel, ViewModel() {

    private val _loadAllDataLiveData = MediatorLiveData<Event<List<DictionaryEntity>>>()
    val loadAllDataLiveData: LiveData<Event<List<DictionaryEntity>>> get() = _loadAllDataLiveData

    private val _itemTouchLiveData = MediatorLiveData<Event<DictionaryEntity>>()
    val itemTouchLiveData: LiveData<Event<DictionaryEntity>> get() = _itemTouchLiveData

    private val _backLiveData = MediatorLiveData<Event<Unit>>()
    val backLiveData: LiveData<Event<Unit>> get() = _backLiveData

    private val _openDialogDeleteAllLiveData = MediatorLiveData<Event<Unit>>()
    val openDialogDeleteAllLiveData: LiveData<Event<Unit>> get() = _openDialogDeleteAllLiveData

    private val _showMessageToastLiveData = MediatorLiveData<Event<String>>()
    val showMessageToastLiveData: LiveData<Event<String>> get() = _showMessageToastLiveData

    private val _showMessageEmptyLiveData = MediatorLiveData<Event<String>>()
    val showMessageEmptyLiveData: LiveData<Event<String>> get() = _showMessageEmptyLiveData

    private val _loadingLiveData = MediatorLiveData<Event<Boolean>>()
    val loadingLiveData: LiveData<Event<Boolean>> get() = _loadingLiveData

    override fun itemTouch(data: DictionaryEntity) = _itemTouchLiveData.postValue(Event(data))

    override fun delete(data: DictionaryEntity) {
        viewModelScope.launch {
            useCase.delete(data).onEach {
                when (it) {
                    is Responce.Success -> {
                        _loadingLiveData.postValue(Event(false))
                        _loadAllDataLiveData.postValue(Event(it.data))
                    }
                    is Responce.Error -> {
                        _loadingLiveData.postValue(Event(false))
                        _showMessageToastLiveData.postValue(Event(it.error))
                    }
                    is Responce.Loading -> {
                        _loadingLiveData.postValue(Event(true))
                    }
                    is Responce.Message -> {
                        _loadingLiveData.postValue(Event(false))
                        _showMessageToastLiveData.postValue(Event(it.message))
                    }
                }
            }.catch {
                _loadingLiveData.postValue(Event(false))
                _showMessageEmptyLiveData.postValue(Event("Error please try again"))
            }.launchIn(viewModelScope)
        }
    }

    override fun returnToActive(data: DictionaryEntity) {
        viewModelScope.launch {
            useCase.update(data).onEach {
                when (it) {
                    is Responce.Success -> {
                        _loadingLiveData.postValue(Event(false))
                        _loadAllDataLiveData.postValue(Event(it.data))
                    }
                    is Responce.Error -> {
                        _loadingLiveData.postValue(Event(false))
                        _showMessageToastLiveData.postValue(Event(it.error))
                    }
                    is Responce.Loading -> {
                        _loadingLiveData.postValue(Event(true))
                    }
                    is Responce.Message -> {
                        _loadingLiveData.postValue(Event(false))
                        _showMessageEmptyLiveData.postValue(Event(it.message))
                    }
                }
            }.catch {
                _loadingLiveData.postValue(Event(false))
                _showMessageEmptyLiveData.postValue(Event("Error please try again"))
            }.launchIn(viewModelScope)
        }
    }

    override fun clearAll() {
        _openDialogDeleteAllLiveData.postValue(Event(Unit, {
            viewModelScope.launch {
                useCase.deleteAll().onEach {
                    when (it) {
                        is Responce.Success -> {
                            _loadingLiveData.postValue(Event(false))
                            _loadAllDataLiveData.postValue(Event(it.data))
                        }
                        is Responce.Error -> {
                            _loadingLiveData.postValue(Event(false))
                            _showMessageToastLiveData.postValue(Event(it.error))
                        }
                        is Responce.Loading -> {
                            _loadingLiveData.postValue(Event(true))
                        }
                        is Responce.Message -> {
                            _loadingLiveData.postValue(Event(false))
                            _showMessageToastLiveData.postValue(Event(it.message))
                        }
                    }
                }.catch {
                    _loadingLiveData.postValue(Event(false))
                    _showMessageEmptyLiveData.postValue(Event("Error please try again"))
                }.launchIn(viewModelScope)
            }
        }))
    }

    override fun back() = _backLiveData.postValue(Event(Unit))

    override fun getArchiveList() {
        viewModelScope.launch {
            useCase.getAllArxiveList().onEach {
                when (it) {
                    is Responce.Success -> {
                        _loadingLiveData.postValue(Event(false))
                        _loadAllDataLiveData.postValue(Event(it.data))
                    }
                    is Responce.Error -> {
                        _loadingLiveData.postValue(Event(false))
                        _showMessageToastLiveData.postValue(Event(it.error))
                    }
                    is Responce.Loading -> {
                        _loadingLiveData.postValue(Event(true))
                    }
                    is Responce.Message -> {
                        _loadingLiveData.postValue(Event(false))
                        _showMessageEmptyLiveData.postValue(Event(it.message))
                    }
                }
            }.catch {
                _loadingLiveData.postValue(Event(false))
                _showMessageEmptyLiveData.postValue(Event("Error please try again"))
            }.launchIn(viewModelScope)
        }
    }
}