package com.example.dictionary.domen.impl.word

import com.example.dictionary.data.model.DataWord
import com.example.dictionary.data.model.changeWordEntityListToDataWord
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.data.source.local.room.entity.WordEntity
import com.example.dictionary.domen.repository.word.RepositoryWordsPlay
import com.example.dictionary.domen.usecase_word.UseCaseWordsPlay
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UseCaseWordsPlayImplament @Inject constructor(private var repository: RepositoryWordsPlay) : UseCaseWordsPlay {
    override fun getList(id: Long): Flow<Responce<List<DataWord>>> = flow {
        try {
            emit(Responce.Loading(true))
            val list = repository.getList(id).changeWordEntityListToDataWord(true, true)
            if (list.isNotEmpty()) {
                emit(Responce.Success(list))
            } else
                emit(Responce.Error("List is empty"))
        } catch (e: Exception) {
            emit(Responce.Error(e.message.toString()))
        }
    }

    override fun getDictionary(id: Long): Flow<Responce<DictionaryEntity>> = flow {
        try {
            emit(Responce.Loading(true))
            val data = repository.getDictionary(id)
            emit(Responce.Success(data))
        } catch (e: Exception) {
            emit(Responce.Error(e.message.toString()))
        }
    }

    override fun done(data: DataWord, text: String, id: Long): Flow<Responce<List<DataWord>>> = flow {
        try {
            emit(Responce.Loading(true))
            if (data.wordFirst.equals(text) || data.wordSecond.equals(text)) {
                val dt = WordEntity(data.id, data.wordFirst, data.wordSecond, data.example, data.dictionaryId, ++data.learnCount)
                repository.updateWord(dt)
                val dictionary = repository.getDictionary(id)
                val learnCaunt = repository.getDictionaryLearnWordsCount(id)
                val allCaunt = repository.getDictionaryWordsCount(id)
                dictionary.learnPracent = learnCaunt * 100 / allCaunt
                repository.updateDictionary(dictionary)
                val ls = repository.getList(id).changeWordEntityListToDataWord(true, true)
                if (ls.isNotEmpty()) {
                    emit(Responce.Success(ls))
                } else
                    emit(Responce.Error("List is empty"))
            }
        } catch (e: Exception) {
            emit(Responce.Error(e.message.toString()))
        }
    }
}