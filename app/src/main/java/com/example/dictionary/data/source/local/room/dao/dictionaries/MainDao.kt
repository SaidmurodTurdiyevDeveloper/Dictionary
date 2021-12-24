package com.example.dictionary.data.source.local.room.dao.dictionaries

import androidx.room.Dao
import androidx.room.Query
import com.example.dictionary.data.source.local.room.dao.BaseDao
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity

@Dao
interface MainDao : BaseDao<DictionaryEntity> {
    @Query("SELECT * FROM Dictionaries WHERE (:oneId=languageIdOne AND :twoId=languageIdTwo  OR :oneId=languageIdTwo AND :twoId=languageIdOne) AND isDelete==0")
    suspend fun getDictionaries(oneId: Int, twoId: Int): List<DictionaryEntity>

    @Query("SELECT count(*) FROM WORDDATABASE WHERE learnedCount>3")
    suspend fun getLearnedCount(): Long

    @Query("SELECT * FROM Dictionaries WHERE id=:id LIMIT 1")
    suspend fun getDictionaryById(id: Long): DictionaryEntity

}