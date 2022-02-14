package com.example.dictionary.domen.repository.dictionary

import com.example.dictionary.data.source.local.room.entity.DictionaryEntity

interface RepositoryDictionaryInfo {
    suspend fun getDictionary(id: Long): DictionaryEntity
    suspend fun update(data: DictionaryEntity)
}