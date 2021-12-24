package com.example.dictionary.data.source.local.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: T): Long

    @Update
    suspend fun update(data: T)

    @Delete
    suspend fun delete(data: T): Int

    @Delete
    suspend fun deleteAll(list: List<T>): Int
}