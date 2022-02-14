package com.example.dictionary.data.repostory.dictionary

import com.example.dictionary.data.source.local.room.dao.dictionaries.DictionaryItemRoomDatabaseDao
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.domen.repository.dictionary.RepositoryDictionaryInfo
import javax.inject.Inject

class RepositoryDictionaryInfoImplament @Inject constructor(private var database: DictionaryItemRoomDatabaseDao) : RepositoryDictionaryInfo {
    override suspend fun getDictionary(id: Long): DictionaryEntity = database.getDictionaryById(id)

    override suspend fun update(data: DictionaryEntity) = database.update(data)
}