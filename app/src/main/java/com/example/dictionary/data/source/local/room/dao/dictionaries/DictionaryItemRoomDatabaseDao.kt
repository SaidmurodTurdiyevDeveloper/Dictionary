package com.example.dictionary.data.source.local.room.dao.dictionaries

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity

@Dao
interface DictionaryItemRoomDatabaseDao {
    @Query("Select * From dictionaries where id =:id Limit 1")
    suspend fun getDictionaryById(id: Long): DictionaryEntity

    @Update
    suspend fun update(data: DictionaryEntity)

    @Query("Select count(id) From worddatabase where dictionaryId=:id")
    suspend fun getWordsCountOfDictinary(id: Long): Int

    @Query("SELECT count(id) FROM WORDDATABASE WHERE (learnedCount>3 and dictionaryId=:id)")
    suspend fun geDictionaryWordsLearnCount(id: Long): Int
}