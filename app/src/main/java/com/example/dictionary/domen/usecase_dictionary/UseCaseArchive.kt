package com.example.dictionary.domen.usecase_dictionary

import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow

interface UseCaseArchive {
    fun getAllArxiveList(): Flow<Responce<List<DictionaryEntity>>>
    fun delete(data: DictionaryEntity): Flow<Responce<List<DictionaryEntity>>>
    fun update(data: DictionaryEntity): Flow<Responce<List<DictionaryEntity>>>
    fun deleteAll(): Flow<Responce<List<DictionaryEntity>>>
    fun getSize(): Flow<Int>
}