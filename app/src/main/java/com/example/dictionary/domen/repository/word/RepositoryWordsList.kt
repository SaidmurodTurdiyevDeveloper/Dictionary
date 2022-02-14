package com.example.dictionary.domen.repository.word

import com.example.dictionary.data.source.local.room.entity.WordEntity

interface RepositoryWordsList {
    suspend fun getWordList(id: Long): List<WordEntity>
}