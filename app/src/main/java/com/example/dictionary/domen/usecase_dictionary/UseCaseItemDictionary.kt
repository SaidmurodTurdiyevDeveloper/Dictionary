package com.example.dictionary.domen.usecase_dictionary

import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow

interface UseCaseItemDictionary {
    fun getDictionary(id: Long): Flow<Responce<DictionaryEntity>>
    fun getDictionaryLearnCount(id: Long): Flow<Responce<String>>
    suspend fun getCountryWithId(position: Int): DataCountry
}