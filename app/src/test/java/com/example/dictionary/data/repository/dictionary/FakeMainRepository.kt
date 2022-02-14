package com.example.dictionary.data.repository.dictionary

import com.example.dictionary.contracts.dictionary.ContractMain
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity

class FakeMainRepository : ContractMain.Model {
    private var dictionaryList = ArrayList<DictionaryEntity>()
    private var isDayNightMode = false
    private var firstCountryId = 0
    private var secondCountryId = 0
    private var learningCount = 0L

    fun setLearningCount(count: Long) {
        learningCount = count
    }

    fun setFirstCountryId(id: Int) {
        firstCountryId = id
    }

    fun setSecondCountryId(id: Int) {
        secondCountryId = id
    }

    override suspend fun getCountOfWordsWhichLearned(): Long = learningCount

    override suspend fun addNewDictionary(data: DictionaryEntity): Long {
        return if (0 > getPosition(data)) {
            dictionaryList.add(data)
            data.id
        } else
            -1
    }

    override suspend fun updateDictionary(data: DictionaryEntity) {
        val position = getPosition(data)
        dictionaryList.set(position, data)
    }

    override suspend fun encaseToArchive(data: DictionaryEntity) {
        val position = getPosition(data)
        dictionaryList.set(position, data)
    }

    override fun getIsDayOrNight(): Boolean = isDayNightMode

    override fun setDayOrNight(cond: Boolean) {
        isDayNightMode = cond
    }

    override suspend fun getActiveListOfDictionary(firstId: Int, secondId: Int): List<DictionaryEntity> = dictionaryList.filter { it.isDelete == 0 }

    override fun getLanguageOne(): Int = firstCountryId

    override fun getLanguageTwo(): Int = secondCountryId

    private fun getPosition(data: DictionaryEntity): Int {
        for (i in 0 until dictionaryList.size) {
            if (dictionaryList[i].id == data.id)
                return i
        }
        return -1
    }
}