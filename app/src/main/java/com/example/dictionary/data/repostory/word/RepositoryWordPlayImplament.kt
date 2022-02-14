package com.example.dictionary.data.repostory.word

import com.example.dictionary.data.source.local.room.dao.dictionaries.DictionaryItemRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.word.WordRoomDatabaseDao
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.data.source.local.room.entity.WordEntity
import com.example.dictionary.domen.repository.word.RepositoryWordsPlay
import javax.inject.Inject

class RepositoryWordPlayImplament @Inject constructor(
    private var databaseWord: WordRoomDatabaseDao,
    private var databaseDictionary: DictionaryItemRoomDatabaseDao
) : RepositoryWordsPlay {
    override suspend fun getList(id: Long): List<WordEntity> = databaseWord.getList(id)

    override suspend fun updateWord(data: WordEntity) = databaseWord.update(data)

    override suspend fun getDictionary(id: Long): DictionaryEntity = databaseDictionary.getDictionaryById(id)

    override suspend fun updateDictionary(data: DictionaryEntity) = databaseDictionary.update(data)

    override suspend fun getDictionaryLearnWordsCount(id: Long): Int = databaseDictionary.geDictionaryWordsLearnCount(id)

    override suspend fun getDictionaryWordsCount(id: Long): Int = databaseDictionary.getWordsCountOfDictinary(id)
}