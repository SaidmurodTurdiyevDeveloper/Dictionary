package com.example.dictionary.domen.impl.word

import com.example.dictionary.data.model.DataWord
import com.example.dictionary.data.model.changeWordEntityListToDataWord
import com.example.dictionary.domen.repository.word.RepositoryWordsList
import com.example.dictionary.domen.usecase_word.UseCaseWordList
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UseCaseWordListImplament @Inject constructor(private var repository: RepositoryWordsList) : UseCaseWordList {

    override fun getWordList(id: Long, isActiveFirst: Boolean, isActiveSecond: Boolean): Flow<Responce<List<DataWord>>> = flow {
        try {
            emit(Responce.Loading(true))
            val list = repository.getWordList(id).changeWordEntityListToDataWord(isActiveFirst, isActiveSecond)
            emit(Responce.Success(list))
        } catch (e: Exception) {
            emit(Responce.Error(e.message.toString()))
        }
    }
}