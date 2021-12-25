package com.example.dictionary.contracts.mixed

import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity

interface ContractDictionaryItem {
    interface Model {
        suspend fun getData(id: Long): DictionaryEntity
        suspend fun getTextInfo(id: Long): String
        suspend fun updateData(data: DictionaryEntity)
        suspend fun getCountryList(): List<DataCountry>
        suspend fun getLearnCountText(): String
    }

    interface View {
        fun loadData(data: DictionaryEntity)
        fun loadDataLearnPracent(text: String)
        fun openInfoText(textInfo: String)
        fun openList(id: Long)
        fun closeWindow()
    }

    interface ViwModel {
        fun openInfo(id: Long)
        fun openList()
        fun loadItem(id: Long)
        fun close()
    }
}