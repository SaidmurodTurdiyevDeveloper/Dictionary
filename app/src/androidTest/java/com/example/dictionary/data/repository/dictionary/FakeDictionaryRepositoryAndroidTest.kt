package com.example.dictionary.data.repository.dictionary

import com.example.dictionary.contracts.dictionary.ContractDictionaryItem
import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.utils.other.MyCountries

class FakeDictionaryRepositoryAndroidTest : ContractDictionaryItem.Model {
    private var existData = DictionaryEntity(1, "Dictionary", "This is fake dictionary", 0, 1, 2, 0)
    private var countries = MyCountries()

    override suspend fun getData(id: Long): DictionaryEntity = if (id == 1L) existData else DictionaryEntity(-1, "", "", 0, 0, 0, 0)

    override suspend fun updateData(data: DictionaryEntity) {
        existData = data
    }

    override suspend fun getCountryList(): List<DataCountry> = countries.getCountries()

    override suspend fun getWordsCount(id: Long): Int = 25

    override suspend fun getLearnWordsCount(id: Long): Int = 23
}