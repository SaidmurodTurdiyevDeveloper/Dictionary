package com.example.dictionary.domen.usecase_word

import com.example.dictionary.data.model.DataWord
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow

interface UseCaseWordList {
    fun getWordList(id: Long, isActiveFirst: Boolean, isActiveSecond: Boolean): Flow<Responce<List<DataWord>>>
}