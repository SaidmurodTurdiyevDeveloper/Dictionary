package com.example.dictionary.data.source.local.room.dao.dictionaries

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity

@Dao
interface ArxiveRoomDatabaseDao {
    @Query("SELECT * FROM Dictionaries Where isDelete=1")
    suspend fun getArxiveList(): List<DictionaryEntity>

    @Delete
    suspend fun delete(data: DictionaryEntity): Int

    @Delete
    suspend fun deleteAll(list: List<DictionaryEntity>): Int

    @Update
    suspend fun update(data: DictionaryEntity)

    @Query("SELECT Count(id) From Dictionaries Where isDelete=1 ")
    suspend fun getSize():Int
}