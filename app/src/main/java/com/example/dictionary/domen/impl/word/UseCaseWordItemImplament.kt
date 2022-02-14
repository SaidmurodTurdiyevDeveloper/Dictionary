package com.example.dictionary.domen.impl.word

import com.example.dictionary.data.source.local.room.entity.WordEntity
import com.example.dictionary.domen.repository.word.RepositoryWordItem
import com.example.dictionary.domen.usecase_word.UseCaseWordItem
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UseCaseWordItemImplament @Inject constructor(private var repository: RepositoryWordItem) : UseCaseWordItem {
    override fun getWord(id: Long): Flow<Responce<WordEntity>> = flow {
        emit(Responce.Loading(true))
        try {
            val data = repository.getWord(id)
            emit(Responce.Success(data))
        } catch (e: Exception) {
            emit(Responce.Error(e.message.toString()))
        }
        emit(Responce.Loading(false))
    }

    override fun update(data: WordEntity, text: String): Flow<Responce<WordEntity>> = flow {
        emit(Responce.Loading(true))
        try {
            if (data.example.equals(text))
                emit(Responce.Message("Data does not changed"))
            else {
                data.example = text
                repository.update(data)
                val newData = repository.getWord(data.id)
                emit(Responce.Success(newData))
            }
        } catch (e: Exception) {
            emit(Responce.Error(e.message.toString()))
        }
        emit(Responce.Loading(false))
    }
}