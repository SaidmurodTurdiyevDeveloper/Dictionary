package com.example.dictionary.domen.repository.word

import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.data.source.local.room.entity.WordEntity

interface RepositoryWordsPlay {
    suspend fun getList(id: Long): List<WordEntity>
    suspend fun updateWord(data: WordEntity)
    suspend fun getDictionary(id: Long): DictionaryEntity
    suspend fun getDictionaryLearnWordsCount(id: Long): Int
    suspend fun getDictionaryWordsCount(id: Long): Int
    suspend fun updateDictionary(data: DictionaryEntity)
}