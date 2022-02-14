package com.example.dictionary.domen.usecase_dictionary

import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow

/**
 * Saidmurod Turdiyev 1/16/2022/ 7:31
 * */
interface UseCaseMain {
    suspend fun getDictionaryList(): Flow<Responce<List<DictionaryEntity>>>
    suspend fun getCountLearnedWords(): Flow<String>
    suspend fun addDictionary(data: DictionaryEntity): Flow<Responce<List<DictionaryEntity>>>
    suspend fun removeListDictionary(list: List<DictionaryEntity>): Flow<Responce<List<DictionaryEntity>>>
    suspend fun removeItemDictionary(data: DictionaryEntity): Flow<Responce<List<DictionaryEntity>>>
    suspend fun updateDictionary(oldData: DictionaryEntity, newData: DictionaryEntity): Flow<Responce<List<DictionaryEntity>>>
    suspend fun removeDictionaryActive(data: DictionaryEntity): Flow<Responce<List<DictionaryEntity>>>
    suspend fun deleteSelectedList(): Flow<Responce<List<DictionaryEntity>>>
    fun selectItem(data: DictionaryEntity): Flow<Responce<List<DictionaryEntity>>>
    fun selectAllItem(cond: Boolean): Flow<Responce<List<DictionaryEntity>>>
    suspend fun changeDayNight(): Flow<Boolean>
    fun cancelSelect()
    fun selectedItemcount(): Int
    fun selectedItems(): List<DictionaryEntity>
}