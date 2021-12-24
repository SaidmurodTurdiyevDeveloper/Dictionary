package com.example.dictionary.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.dictionary.data.source.local.room.entity.WordEntity

@Dao
interface WordDao : BaseDao<WordEntity> {
    @Query("SELECT * FROM WordDataBase WHERE id=:id")
    suspend fun getList(id: Long): List<WordEntity>
}