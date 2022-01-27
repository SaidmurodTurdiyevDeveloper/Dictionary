package com.example.dictionary.contracts.dictionary

import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity

interface ContractDictionaryItem {
    interface Model {
        suspend fun getData(id: Long): DictionaryEntity
        suspend fun updateData(data: DictionaryEntity)
        suspend fun getCountryList(): List<DataCountry>
        suspend fun getWordsCount(id: Long): Int
        suspend fun getLearnWordsCount(id: Long): Int
    }

    interface View {
        fun loadData(data: DictionaryEntity)
        fun loadDataLearnPracent(text: String)
        fun openInfoText(textInfo: String)
        fun openList(id: Long)
        fun closeWindow()
    }

    interface ViwModel {
        fun openList()
        fun openInfo(id: Long)
        fun loadItem(id: Long)
        fun close()
    }
}