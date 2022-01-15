package com.example.dictionary.data.repostory.dictionary

import com.example.dictionary.contracts.dictionary.ContractMain
import com.example.dictionary.data.source.local.room.dao.dictionaries.MainRoomDatabaseDao
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.data.source.local.shared.SharedDatabese
import com.example.dictionary.utils.MyStringObjects
import javax.inject.Inject

class RepostoryMain @Inject constructor(
    private var database: MainRoomDatabaseDao,
    private var localStore: SharedDatabese
) : ContractMain.Model {
    private var array = ArrayList<DictionaryEntity>()
    private var checkedCaount = 0

    override suspend fun getCountOfWordsWhichLearned(): Long {
        return database.getLearnedCount()
    }

    override suspend fun addNewDictionary(data: DictionaryEntity) {
        data.languageIdOne = getLanguageOne()
        data.languageIdTwo = getLanguageTwo()
       database.insert(data)
    }

    override suspend fun updateDictionary(data: DictionaryEntity) {
        database.update(data)
    }

    override suspend fun encaseToArchive(data: DictionaryEntity) {
        data.isDelete = 1
        database.update(data)
    }

    override suspend fun getActiveListOfDictionary(): List<DictionaryEntity> {
        val ls = database.getDictionaries(
            getLanguageOne(),
            getLanguageTwo()
        )
        return ls
    }

    private fun getLanguageOne(): Int {
        return localStore.getIntData(MyStringObjects.LANGUAGE_ONE)
    }

    private fun getLanguageTwo(): Int {
        return localStore.getIntData(MyStringObjects.LANGUAGE_TWO)
    }

    override fun getIsDayOrNight(): Boolean {
        return localStore.getBoolenData(MyStringObjects.DAY_NIGHT)
    }

    override fun setDayOrNight(cond: Boolean) {
        localStore.setBoolenData(MyStringObjects.DAY_NIGHT, cond)
    }

    override suspend fun encaseListtoArchive() {
        array.forEach {
            if (it.isSelect) {
                this.encaseToArchive(it)
            }
        }
    }

    override suspend fun selectAll(): List<DictionaryEntity> {
        val list = getActiveListOfDictionary()
        list.forEach {
            it.isSelect = true
        }
        checkedCaount = list.size
        array.clear()
        array.addAll(list)
        return array.toMutableList()
    }

    override suspend fun cancelSelected() {
        array.clear()
        checkedCaount = 0
    }

    override suspend fun check(position: Int): Boolean {
        val d = array[position]
        if (d.isSelect)
            checkedCaount--
        else
            checkedCaount++
        val data = DictionaryEntity(
            d.id,
            d.name,
            d.dataInfo,
            d.learnPracent,
            d.languageIdOne,
            d.languageIdTwo,
            d.isDelete
        )
        data.isSelect = !d.isSelect
        array[position] = data
        return array.size == checkedCaount
    }

    override suspend fun addArrays(): List<DictionaryEntity> {
        array.addAll(getActiveListOfDictionary())
        return array.toMutableList()
    }

    override suspend fun getSelectedArray(): List<DictionaryEntity> {
        return array.toMutableList()
    }
}