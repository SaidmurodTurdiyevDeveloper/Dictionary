package com.example.dictionary.ui.viewModel.dictionary

import androidx.lifecycle.*
import com.example.dictionary.contracts.dictionary.ContractMain
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMain @Inject constructor(
    private var model: ContractMain.Model
) : ContractMain.ViewModel, ViewModel() {

    private val _loadLiveData = MediatorLiveData<Event<List<DictionaryEntity>>>()
    val loadLiveData: LiveData<Event<List<DictionaryEntity>>> get() = _loadLiveData

    private val _clickItemLiveData = MediatorLiveData<Event<Long>>()
    val clickItemLiveData: LiveData<Event<Long>> get() = _clickItemLiveData

    private val _openItemLiveData = MediatorLiveData<Event<Long>>()
    val openItemLiveData: LiveData<Event<Long>> get() = _openItemLiveData

    private val _deleteLiveData = MutableLiveData<Event<DictionaryEntity>>()
    val deleteLiveData: LiveData<Event<DictionaryEntity>> get() = _deleteLiveData

    private val _darkLightClickLiveData = MutableLiveData<Event<Boolean>>()
    val darkLightClickLiveData: LiveData<Event<Boolean>> get() = _darkLightClickLiveData

    private val _editLiveData = MutableLiveData<Event<DictionaryEntity>>()
    val editLiveData: LiveData<Event<DictionaryEntity>> get() = _editLiveData

    private val _openHomeLiveData = MutableLiveData<Event<Unit>>()
    val openHomeLiveData: LiveData<Event<Unit>> get() = _openHomeLiveData

    private val _openArxiveLiveData = MutableLiveData<Event<Unit>>()
    val openArxiveLiveData: LiveData<Event<Unit>> get() = _openArxiveLiveData

    private val _addLiveData = MutableLiveData<Event<Unit>>()
    val addLiveData: LiveData<Event<Unit>> get() = _addLiveData

    private val _openSettingLiveData = MutableLiveData<Event<Unit>>()
    val openSettingLiveData: LiveData<Event<Unit>> get() = _openSettingLiveData

    private val _openGameLiveData = MutableLiveData<Event<Unit>>()
    val openGameLiveData: LiveData<Event<Unit>> get() = _openGameLiveData

    private val _openChangeLanguageLiveData = MutableLiveData<Event<Unit>>()
    val openChangeLanguageLiveData: LiveData<Event<Unit>> get() = _openChangeLanguageLiveData

    private val _openInfoLiveData = MutableLiveData<Event<Unit>>()
    val openInfoLiveData: LiveData<Event<Unit>> get() = _openInfoLiveData

    private val _learnCountLiveData = MutableLiveData<Event<Long>>()
    val learnCountLiveData: LiveData<Event<Long>> get() = _learnCountLiveData

    private val _openSelectedActionBarLiveData = MutableLiveData<Event<Unit>>()
    val openSelectedActionBarLiveData: LiveData<Event<Unit>> get() = _openSelectedActionBarLiveData

    private var _closeActionBarLiveData = MediatorLiveData<Event<Unit>>()
    val closeActionBarLiveData get() = _closeActionBarLiveData

    private val _deleteAllActionBarLiveData = MediatorLiveData<Event<Unit>>()
    val deleteAllActionBarLiveData get() = _deleteAllActionBarLiveData

    private val _selectAllBarLiveData = MediatorLiveData<Event<Boolean>>()
    val selectAllBarLiveData get() = _selectAllBarLiveData

    fun loadData() {
        viewModelScope.launch {
            _loadLiveData.postValue(Event(model.getList()))
            _learnCountLiveData.postValue(Event(model.getLearnCount()))
        }
    }

    override fun clickItem(id: Long) {
        _clickItemLiveData.postValue(Event(id))
    }

    override fun delete(data: DictionaryEntity) {
        viewModelScope.launch {
            model.delete(data)
            _loadLiveData.postValue(Event(model.getList()))
        }
    }

    override fun remove(data: DictionaryEntity) {
        _deleteLiveData.postValue(Event(data))
    }


    override fun darkLightClick() {
        val cond: Boolean = !model.getDayNight()
        model.setDayNight(cond)
        _darkLightClickLiveData.postValue(Event(cond))
    }

    override fun add() {
        _addLiveData.value = Event(Unit)
    }

    override fun addItem(data: DictionaryEntity) {
        viewModelScope.launch {
            model.add(data)
            _loadLiveData.postValue(Event(model.getList()))
        }
    }

    override fun edit(oldData: DictionaryEntity) {
        _editLiveData.value = Event(oldData)
    }

    override fun update(newData: DictionaryEntity) {
        viewModelScope.launch {
            model.edit(newData)
            _loadLiveData.postValue(Event(model.getList()))
        }
    }

    override fun openHome() {
        _openHomeLiveData.value = Event(Unit)
    }

    override fun openArxive() {
        _openArxiveLiveData.value = Event(Unit)
    }

    override fun openSetting() {
        _openSettingLiveData.value = Event(Unit)
    }

    override fun openGame() {
        _openGameLiveData.value = Event(Unit)
    }

    override fun openChangeLanguage() {
        _openChangeLanguageLiveData.value = Event(Unit)
    }

    override fun openInfo() {
        _openInfoLiveData.value = Event(Unit)
    }

    override fun deleteAll() {
        viewModelScope.launch {
            model.deleteAll()
            _loadLiveData.postValue(Event(model.getList()))
        }
    }

    override fun checkAll(cond: Boolean) {
        viewModelScope.launch {
            model.selectAll()
            _loadLiveData.postValue(Event(model.getSelectedArray()))
            _selectAllBarLiveData.postValue(Event(cond))

        }

    }

    override fun openItem(id: Long) {
        _openItemLiveData.postValue(Event(id))
    }

    override fun cancelSelected() {
        viewModelScope.launch {
            model.cancelSelected()
            _closeActionBarLiveData.postValue(Event(Unit))
            _loadLiveData.postValue(Event(model.getList()))
        }
    }

    override fun select(position: Int) {
        viewModelScope.launch {
            val cond = model.check(position)
            _selectAllBarLiveData.postValue(Event(cond))
            _loadLiveData.postValue(Event(model.getSelectedArray()))
        }
    }

    override fun onceCheck(position: Int) {
        viewModelScope.launch {
            model.cancelSelected()
            model.addArrays()
            val cond = model.check(position)
            _loadLiveData.postValue(Event(model.getSelectedArray()))
            _openSelectedActionBarLiveData.postValue(Event(Unit))
            _selectAllBarLiveData.postValue(Event(cond))
        }
    }
}