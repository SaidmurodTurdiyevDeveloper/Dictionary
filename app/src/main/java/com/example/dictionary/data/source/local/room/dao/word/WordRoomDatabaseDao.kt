package com.example.dictionary.data.source.local.room.dao.word

import androidx.room.Dao
import androidx.room.Query
import com.example.dictionary.data.source.local.room.dao.BaseDao
import com.example.dictionary.data.source.local.room.entity.WordEntity

@Dao
interface WordRoomDatabaseDao : BaseDao<WordEntity> {
    @Query("SELECT * FROM WordDataBase WHERE dictionaryId=:id")
    suspend fun getList(id: Long): List<WordEntity>
}