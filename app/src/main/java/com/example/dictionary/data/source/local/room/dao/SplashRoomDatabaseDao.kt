package com.example.dictionary.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.example.dictionary.data.source.local.room.entity.WordEntity

@Dao
interface SplashRoomDatabaseDao {
    @Query("SELECT WordDataBase.* FROM WordDataBase LEFT OUTER JOIN Dictionaries ON WordDataBase.dictionaryId = Dictionaries.id where Dictionaries.id is NULL")
    suspend fun getEmptyWords(): List<WordEntity>

    @Delete
    suspend fun deleteAll(list: List<WordEntity>): Int
}