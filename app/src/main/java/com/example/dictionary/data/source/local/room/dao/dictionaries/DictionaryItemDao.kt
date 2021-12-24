package com.example.dictionary.data.source.local.room.dao.dictionaries

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity

@Dao
interface DictionaryItemDao {
    
    @Query("SELECT * FROM Dictionaries WHERE id=:id LIMIT 1")
    suspend fun getDictionaryById(id: Long): DictionaryEntity

    @Update
    suspend fun update(data: DictionaryEntity)
}