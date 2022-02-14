package com.example.dictionary.domen.repository.word

import com.example.dictionary.data.source.local.room.entity.WordEntity

interface RepositoryWordItem {
    suspend fun getWord(id: Long): WordEntity
    suspend fun update(data: WordEntity)
}