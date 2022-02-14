package com.example.dictionary.data.repository.word

import com.example.dictionary.data.source.local.room.entity.WordEntity
import com.example.dictionary.domen.repository.word.RepositoryWordsLearn

class FakeRepositoryWordLearnImplament : RepositoryWordsLearn {
    var list = ArrayList<WordEntity>()

    init {
        list.add(WordEntity(1, "Hello", "Salom", "Hello World", 1, 1))
    }

    override suspend fun getWordsList(id: Long): List<WordEntity> {
       return list
    }

    override suspend fun addNewWord(data: WordEntity): Long {
        data.id = list.size.toLong()
        list.add(data)
        return data.id
    }

    override suspend fun delete(data: WordEntity): Int {
        val lastSize = list.size
        list.remove(data)
        return lastSize - list.size
    }

    override suspend fun update(data: WordEntity) {
        val position = getPosition(data)
        list.set(position, data)
    }

    private fun getPosition(data: WordEntity): Int {
        for (i in 0 until list.size) {
            if (list[i].id == data.id)
                return i
        }
        return -1
    }
}