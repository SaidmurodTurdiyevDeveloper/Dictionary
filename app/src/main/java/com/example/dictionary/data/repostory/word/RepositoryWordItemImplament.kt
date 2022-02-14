package com.example.dictionary.data.repostory.word

import com.example.dictionary.data.source.local.room.dao.word.WordItemRoomDatabaseDao
import com.example.dictionary.data.source.local.room.entity.WordEntity
import com.example.dictionary.domen.repository.word.RepositoryWordItem
import javax.inject.Inject

class RepositoryWordItemImplament @Inject constructor(private var database: WordItemRoomDatabaseDao) : RepositoryWordItem {
    override suspend fun getWord(id: Long): WordEntity = database.getWordItemwothId(id)

    override suspend fun update(data: WordEntity) = database.updateWord(data)
}