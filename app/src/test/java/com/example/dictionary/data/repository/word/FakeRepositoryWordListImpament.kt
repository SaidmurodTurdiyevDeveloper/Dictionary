package com.example.dictionary.data.repository.word

import com.example.dictionary.data.source.local.room.entity.WordEntity
import com.example.dictionary.domen.repository.word.RepositoryWordsList

class FakeRepositoryWordListImpament : RepositoryWordsList {
    var list = ArrayList<WordEntity>()
    init {
        list.add(WordEntity(1, "Hello", "Salom", "Hello World", 1, 1))
    }
    override suspend fun getWordList(id: Long): List<WordEntity> = list.filter {
        id == it.id
    }
}