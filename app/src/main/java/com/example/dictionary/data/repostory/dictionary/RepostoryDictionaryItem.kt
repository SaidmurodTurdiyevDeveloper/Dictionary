package com.example.dictionary.data.repostory.dictionary

import com.example.dictionary.contracts.dictionary.ContractDictionaryItem
import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.data.source.local.room.dao.dictionaries.DictionaryItemRoomDatabaseDao
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.utils.other.MyCountries
import javax.inject.Inject

class RepostoryDictionaryItem @Inject constructor(
    private var countries: MyCountries,
    private var databse: DictionaryItemRoomDatabaseDao
) :
    ContractDictionaryItem.Model {

    override suspend fun getData(id: Long): DictionaryEntity = databse.getDictionaryById(id)

    override suspend fun getCountryList(): List<DataCountry> = countries.getCountries()

    override suspend fun getWordsCount(id: Long): Int = databse.getWordsCountOfDictinary(id)

    override suspend fun getLearnWordsCount(id: Long): Int = databse.geDictionaryWordsLearnCount(id)

    override suspend fun updateData(data: DictionaryEntity) = databse.update(data)

}
