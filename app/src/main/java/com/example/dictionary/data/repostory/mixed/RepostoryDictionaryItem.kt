package com.example.dictionary.data.repostory.mixed

import com.example.dictionary.contracts.mixed.ContractDictionaryItem
import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.data.source.local.room.dao.dictionaries.DictionaryItemRoomDatabaseDao
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.utils.MyCountries
import javax.inject.Inject

class RepostoryDictionaryItem @Inject constructor(
    private var countries: MyCountries,
    private var databse: DictionaryItemRoomDatabaseDao
) :
    ContractDictionaryItem.Model {

    override suspend fun getData(id: Long): DictionaryEntity = databse.getDictionaryById(id)

    override suspend fun getTextInfo(id: Long): String = databse.getDictionaryById(id).dataInfo

    override suspend fun getCountryList(): List<DataCountry> = countries.getCountries()

    override suspend fun getLearnCountText(): String {
       return "Nothing"
    }

    override suspend fun updateData(data: DictionaryEntity) = databse.update(data)
}