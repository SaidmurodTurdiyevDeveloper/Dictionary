package com.example.dictionary.data.repostory.word

import com.example.dictionary.data.source.local.room.dao.word.WordRoomDatabaseDao
import com.example.dictionary.data.source.local.room.entity.WordEntity
import com.example.dictionary.domen.repository.word.RepositoryWordsList
import javax.inject.Inject

class RepositoryWordListImplament @Inject constructor(private var databaseDao: WordRoomDatabaseDao) : RepositoryWordsList {
    override suspend fun getWordList(id: Long): List<WordEntity> = databaseDao.getList(id)
}