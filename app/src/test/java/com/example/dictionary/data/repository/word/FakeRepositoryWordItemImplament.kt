package com.example.dictionary.data.repository.word

import com.example.dictionary.data.source.local.room.entity.WordEntity
import com.example.dictionary.domen.repository.word.RepositoryWordItem

class FakeRepositoryWordItemImplament : RepositoryWordItem {
    var word = WordEntity(1, "Hello", "Salom", "Hello World", 1, 1)
    var empty = WordEntity(-1, "None", "None", "", -1, -1)

    override suspend fun getWord(id: Long): WordEntity {
        return if (id == word.id) word else empty
    }

    override suspend fun update(data: WordEntity) {
        if (word.id == data.id)
            word = data
    }
}