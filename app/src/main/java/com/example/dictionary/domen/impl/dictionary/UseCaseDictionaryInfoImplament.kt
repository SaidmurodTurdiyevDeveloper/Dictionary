package com.example.dictionary.domen.impl.dictionary

import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.domen.repository.dictionary.RepositoryDictionaryInfo
import com.example.dictionary.domen.usecase_dictionary.UseCaseDictionaryInfo
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UseCaseDictionaryInfoImplament @Inject constructor(private var repository: RepositoryDictionaryInfo) : UseCaseDictionaryInfo {
    override fun getDictionary(id: Long): Flow<Responce<DictionaryEntity>> = flow {
        emit(Responce.Loading(true))
        try {
            val data = repository.getDictionary(id)
            emit(Responce.Success(data))
        } catch (e: Exception) {
            emit(Responce.Error(e.message.toString()))
        }
        emit(Responce.Loading(false))
    }

    override fun update(data: DictionaryEntity, text: String): Flow<Responce<DictionaryEntity>> = flow {
        emit(Responce.Loading(true))
        try {
            if (data.dataInfo.equals(text))
                emit(Responce.Message("The text is same with old"))
            else {
                data.dataInfo = text
                repository.update(data)
            }
            emit(Responce.Success(repository.getDictionary(data.id)))
        } catch (e: Exception) {
            emit(Responce.Error(e.message.toString()))
        }
        emit(Responce.Loading(false))
    }
}