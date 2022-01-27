package com.example.dictionary.data.repostory.dictionary

import com.example.dictionary.contracts.dictionary.ContractMain
import com.example.dictionary.data.source.local.room.dao.dictionaries.MainRoomDatabaseDao
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.data.source.local.shared.SharedDatabese
import com.example.dictionary.utils.other.MyStringObjects
import javax.inject.Inject

class RepostoryMain @Inject constructor(
    private var database: MainRoomDatabaseDao,
    private var localStore: SharedDatabese
) : ContractMain.Model {

    override suspend fun getCountOfWordsWhichLearned(): Long = database.getLearnedCount()

    override suspend fun addNewDictionary(data: DictionaryEntity): Long = database.insert(data)

    override suspend fun updateDictionary(data: DictionaryEntity) {
        database.update(data)
    }

    override suspend fun encaseToArchive(data: DictionaryEntity) {
        database.update(data)
    }

    override suspend fun getActiveListOfDictionary(firstId: Int, secondId: Int): List<DictionaryEntity> = database.getDictionaries(firstId, secondId)

    override fun getLanguageOne(): Int = localStore.getIntData(MyStringObjects.LANGUAGE_ONE)

    override fun getLanguageTwo(): Int = localStore.getIntData(MyStringObjects.LANGUAGE_TWO)

    override fun getIsDayOrNight(): Boolean = localStore.getBoolenData(MyStringObjects.DAY_NIGHT)

    override fun setDayOrNight(cond: Boolean) = localStore.setBoolenData(MyStringObjects.DAY_NIGHT, cond)

}