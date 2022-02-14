package com.example.dictionary.domen.usecase_word

import com.example.dictionary.data.model.DataWord
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow

interface UseCaseWordsPlay {
    fun getList(id: Long): Flow<Responce<List<DataWord>>>
    fun getDictionary(id: Long): Flow<Responce<DictionaryEntity>>
    fun done(data: DataWord, text: String,id: Long): Flow<Responce<List<DataWord>>>
}