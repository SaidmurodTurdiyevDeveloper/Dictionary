package com.example.dictionary.ui.viewModel.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.contracts.dictionary.ContractArchive
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.domen.UseCaseArchive
import com.example.dictionary.utils.Responce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelArxive @Inject constructor(private var useCase: UseCaseArchive) :
    ContractArchive.ViewModel, ViewModel() {
    private var selectedList = ArrayList<DictionaryEntity>()

    private val _loadAllDataLiveData = MediatorLiveData<Event<List<DictionaryEntity>>>()
    val loadAllDataLiveData: LiveData<Event<List<DictionaryEntity>>> get() = _loadAllDataLiveData

    private val _itemTouchLiveData = MediatorLiveData<Event<DictionaryEntity>>()
    val itemTouchLiveData: LiveData<Event<DictionaryEntity>> get() = _itemTouchLiveData

    private val _backLiveData = MediatorLiveData<Event<Unit>>()
    val backLiveData: LiveData<Event<Unit>> get() = _backLiveData

    private val _openDialogDeleteAllLiveData = MediatorLiveData<Event<Unit>>()
    val openDialogDeleteAllLiveData: LiveData<Event<Unit>> get() = _openDialogDeleteAllLiveData


    private val _showMassageToastLiveData = MediatorLiveData<Event<String>>()
    val showMassageToastLiveData: LiveData<Event<String>> get() = _showMassageToastLiveData

    private val _showMassageEmptyLiveData = MediatorLiveData<Event<String>>()
    val showMassageEmptyLiveData: LiveData<Event<String>> get() = _showMassageEmptyLiveData

    private val _loadingLiveData = MediatorLiveData<Event<Boolean>>()
    val loadingLiveData: LiveData<Event<Boolean>> get() = _loadingLiveData

    override fun itemTouch(data: DictionaryEntity) = _itemTouchLiveData.postValue(Event(data))


    override fun delete(data: DictionaryEntity) {
        viewModelScope.launch {
            useCase.delete(data).onEach {
                when (it) {
                    is Responce.Success -> {
                        _loadingLiveData.postValue(Event(false))
                        if (it.data) {
                            getAllArchiveList()
                        } else {
                            _showMassageToastLiveData.postValue(Event("Data is not delete"))
                        }
                    }
                    is Responce.Error -> {
                        _loadingLiveData.postValue(Event(false))
                        _showMassageToastLiveData.postValue(Event(it.error))
                    }
                    is Responce.Loading -> {
                        _loadingLiveData.postValue(Event(true))
                    }
                    is Responce.Message -> {
                        _loadingLiveData.postValue(Event(false))
                        _showMassageToastLiveData.postValue(Event(it.message))
                    }
                }
            }.catch {
                _loadingLiveData.postValue(Event(false))
                _showMassageEmptyLiveData.postValue(Event("Error please try again"))
            }.launchIn(viewModelScope)
        }
    }

    override fun update(data: DictionaryEntity) {
        viewModelScope.launch {
            useCase.update(data).onEach {
                when (it) {
                    is Responce.Success -> {
                        _loadingLiveData.postValue(Event(false))
                        getAllArchiveList()
                    }
                    is Responce.Error -> {
                        _loadingLiveData.postValue(Event(false))
                        _showMassageToastLiveData.postValue(Event(it.error))
                    }
                    is Responce.Loading -> {
                        _loadingLiveData.postValue(Event(true))
                    }
                    is Responce.Message -> {
                        _loadingLiveData.postValue(Event(false))
                        _showMassageEmptyLiveData.postValue(Event(it.message))
                    }
                }
            }.catch {
                _loadingLiveData.postValue(Event(false))
                _showMassageEmptyLiveData.postValue(Event("Error please try again"))
            }.launchIn(viewModelScope)
        }
    }

    override fun deleteAll() {
        _openDialogDeleteAllLiveData.postValue(Event(Unit, {
            viewModelScope.launch {
                useCase.deleteAll().onEach {
                    when (it) {
                        is Responce.Success -> {
                            _loadingLiveData.postValue(Event(false))
                            if (it.data) {
                                getAllArchiveList()
                            } else {
                                _showMassageToastLiveData.postValue(Event("Data is not delete"))
                            }
                        }
                        is Responce.Error -> {
                            _loadingLiveData.postValue(Event(false))
                            _showMassageToastLiveData.postValue(Event(it.error))
                        }
                        is Responce.Loading -> {
                            _loadingLiveData.postValue(Event(true))
                        }
                        is Responce.Message -> {
                            _loadingLiveData.postValue(Event(false))
                            _showMassageToastLiveData.postValue(Event(it.message))
                        }
                    }
                }.catch {
                    _loadingLiveData.postValue(Event(false))
                    _showMassageEmptyLiveData.postValue(Event("Error please try again"))
                }.launchIn(viewModelScope)
            }
        }))
    }

    override fun back() = _backLiveData.postValue(Event(Unit))


    override fun getAllArchiveList() {
        viewModelScope.launch {
            useCase.getAllArxiveList().onEach {
                when (it) {
                    is Responce.Success -> {
                        _loadingLiveData.postValue(Event(false))
                        _loadAllDataLiveData.postValue(Event(it.data))
                    }
                    is Responce.Error -> {
                        _loadingLiveData.postValue(Event(false))
                        _showMassageToastLiveData.postValue(Event(it.error))
                    }
                    is Responce.Loading -> {
                        _loadingLiveData.postValue(Event(true))
                    }
                    is Responce.Message -> {
                        _loadingLiveData.postValue(Event(false))
                        _showMassageEmptyLiveData.postValue(Event(it.message))
                    }
                }
            }.catch {
                _loadingLiveData.postValue(Event(false))
                _showMassageEmptyLiveData.postValue(Event("Error please try again"))
            }.launchIn(viewModelScope)
        }
    }
}