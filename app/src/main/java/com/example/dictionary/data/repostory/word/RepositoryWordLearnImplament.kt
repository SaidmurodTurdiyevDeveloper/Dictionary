package com.example.dictionary.data.repostory.word

import com.example.dictionary.data.source.local.room.dao.word.WordRoomDatabaseDao
import com.example.dictionary.data.source.local.room.entity.WordEntity
import com.example.dictionary.domen.repository.word.RepositoryWordsLearn
import javax.inject.Inject

class RepositoryWordLearnImplament @Inject constructor(private var database: WordRoomDatabaseDao) : RepositoryWordsLearn {
    override suspend fun getWordsList(id: Long): List<WordEntity> = database.getList(id)

    override suspend fun addNewWord(data: WordEntity): Long = database.insert(data)

    override suspend fun delete(data: WordEntity): Int = database.delete(data)

    override suspend fun update(data: WordEntity) = database.update(data)
}