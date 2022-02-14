package com.example.dictionary.data.source.local.room.dao.word

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.dictionary.data.source.local.room.entity.WordEntity

@Dao
interface WordItemRoomDatabaseDao {
    @Query("SELECT * FROM worddatabase where id=:id LIMIT 1")
    suspend fun getWordItemwothId(id: Long): WordEntity

    @Update
    suspend fun updateWord(data: WordEntity)
}