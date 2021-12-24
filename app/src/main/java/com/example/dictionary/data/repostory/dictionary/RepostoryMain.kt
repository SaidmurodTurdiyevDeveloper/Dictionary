package com.example.dictionary.data.repostory.dictionary

import com.example.dictionary.contracts.dictionary.ContractMain
import com.example.dictionary.data.source.local.room.dao.dictionaries.MainDao
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.data.source.local.shared.SharedDatabese
import com.example.dictionary.utils.MyStringObjects
import javax.inject.Inject

class RepostoryMain @Inject constructor(
    private var database: MainDao,
    private var localStore: SharedDatabese
) : ContractMain.Model {
    private var array = ArrayList<DictionaryEntity>()
    private var checkedCaount = 0

    override suspend fun getLearnCount(): Long {
        return database.getLearnedCount()
    }

    override suspend fun add(data: DictionaryEntity) {
        data.languageIdOne = getLanguageOne()
        data.languageIdTwo = getLanguageTwo()
        val id = database.insert(data)
        data.id = id
    }

    override suspend fun edit(data: DictionaryEntity) {
        database.update(data)
    }

    override suspend fun delete(data: DictionaryEntity) {
        data.isDelete = 1
        database.update(data)
    }

    override suspend fun getList(): List<DictionaryEntity> {
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

    override fun getDayNight(): Boolean {
        return localStore.getBoolenData(MyStringObjects.DAY_NIGHT)
    }

    override fun setDayNight(cond: Boolean) {
        localStore.setBoolenData(MyStringObjects.DAY_NIGHT, cond)
    }

    override suspend fun deleteAll() {
        array.forEach {
            if (it.isSelect) {
                this.delete(it)
            }
        }
    }

    override suspend fun selectAll(): List<DictionaryEntity> {
        val list = getList()
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
        array.addAll(getList())
        return array.toMutableList()
    }

    override suspend fun getSelectedArray(): List<DictionaryEntity> {
        return array.toMutableList()
    }
}