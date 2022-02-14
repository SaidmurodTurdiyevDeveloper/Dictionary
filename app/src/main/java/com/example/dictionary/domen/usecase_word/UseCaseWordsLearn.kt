package com.example.dictionary.domen.usecase_word

import com.example.dictionary.data.model.DataWord
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow

interface UseCaseWordsLearn {

    fun getWordsList(id: Long): Flow<Responce<List<DataWord>>>

    fun addNewData(newdata: DataWord,id: Long): Flow<Responce<List<DataWord>>>

    fun updateData(newdata: DataWord, oldData: DataWord): Flow<Responce<List<DataWord>>>

    fun deleteData(data: DataWord): Flow<Responce<List<DataWord>>>

}