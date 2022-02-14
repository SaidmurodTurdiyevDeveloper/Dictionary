package com.example.dictionary.data.repostory

import com.example.dictionary.contracts.ContractSplash
import com.example.dictionary.data.source.local.room.dao.SplashRoomDatabaseDao
import com.example.dictionary.data.source.local.room.entity.WordEntity
import com.example.dictionary.data.source.local.shared.SharedDatabese
import com.example.dictionary.utils.other.MyStringObjects
import javax.inject.Inject

/**
 * Saidmurod Turdiyev 1/12/2022 6:51 pm
 * */
class RepostorySplash @Inject constructor(
    private var database: SplashRoomDatabaseDao,
    private var localStoradge: SharedDatabese
) : ContractSplash.Model {

    override suspend fun getIsfFirst(): Boolean = localStoradge.getBoolenData(MyStringObjects.FIRST_ENTER_MODE)

    override suspend fun getEmptyWord(): List<WordEntity> = database.getEmptyWords()

    override suspend fun deleteAllEmpty(list: List<WordEntity>): Int = database.deleteAll(list)
}




