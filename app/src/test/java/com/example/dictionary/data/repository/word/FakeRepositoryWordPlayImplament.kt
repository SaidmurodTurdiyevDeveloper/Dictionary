package com.example.dictionary.data.repository.word

import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.data.source.local.room.entity.WordEntity
import com.example.dictionary.domen.repository.word.RepositoryWordsPlay

class FakeRepositoryWordPlayImplament : RepositoryWordsPlay {
    var list = ArrayList<WordEntity>()
    var dictionary = DictionaryEntity(1, "Dictionary", "For Testing", 0, 1, 2, 0)
    var empty = DictionaryEntity(-1, "None", "nothing", 0, -1, -1, 0)

    init {
        list.add(WordEntity(1, "Hello", "Salom", "Hello World", 1, 1))
    }

    override suspend fun getList(id: Long): List<WordEntity> = list.filter {
        id == it.id
    }

    override suspend fun updateWord(data: WordEntity) {
        val position = getPosition(data)
        list.set(position, data)
    }

    override suspend fun getDictionary(id: Long): DictionaryEntity {
        return if (dictionary.id == id) dictionary else empty
    }

    override suspend fun getDictionaryLearnWordsCount(id: Long): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getDictionaryWordsCount(id: Long): Int {
        TODO("Not yet implemented")
    }

    override suspend fun updateDictionary(data: DictionaryEntity) {
        if (dictionary.id == data.id)
            dictionary = data
    }

    private fun getPosition(data: WordEntity): Int {
        for (i in 0 until list.size) {
            if (list[i].id == data.id)
                return i
        }
        return -1
    }
}