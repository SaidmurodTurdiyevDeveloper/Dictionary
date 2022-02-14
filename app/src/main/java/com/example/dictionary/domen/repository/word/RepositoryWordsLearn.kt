package com.example.dictionary.domen.repository.word

import com.example.dictionary.data.source.local.room.entity.WordEntity

interface RepositoryWordsLearn {
    suspend fun getWordsList(id: Long): List<WordEntity>
    suspend fun addNewWord(data: WordEntity): Long
    suspend fun delete(data: WordEntity): Int
    suspend fun update(data: WordEntity)
}