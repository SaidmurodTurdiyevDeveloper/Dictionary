package com.example.dictionary.domen.usecase_dictionary

import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow

interface UseCaseDictionaryInfo {
    fun getDictionary(id: Long): Flow<Responce<DictionaryEntity>>
    fun update(data: DictionaryEntity,text:String): Flow<Responce<DictionaryEntity>>
}