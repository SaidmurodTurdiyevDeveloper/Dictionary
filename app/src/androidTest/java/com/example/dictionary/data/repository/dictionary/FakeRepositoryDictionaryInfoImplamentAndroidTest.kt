package com.example.dictionary.data.repository.dictionary

import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.domen.repository.dictionary.RepositoryDictionaryInfo

class FakeRepositoryDictionaryInfoImplamentAndroidTest : RepositoryDictionaryInfo {
    var dictionary = DictionaryEntity(1, "Dictionary", "For Testing", 0, 1, 2, 0)
    var empty = DictionaryEntity(-1, "None", "nothing", 0, -1, -1, 0)
    override suspend fun getDictionary(id: Long): DictionaryEntity {
        return if (dictionary.id == id) dictionary else empty
    }

    override suspend fun update(data: DictionaryEntity) {
        if (dictionary.id == data.id)
            dictionary = data
    }
}