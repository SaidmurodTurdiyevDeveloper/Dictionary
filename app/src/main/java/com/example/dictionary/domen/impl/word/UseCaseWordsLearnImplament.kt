package com.example.dictionary.domen.impl.word

import com.example.dictionary.data.model.DataWord
import com.example.dictionary.data.model.changeWordEntityListToDataWord
import com.example.dictionary.data.source.local.room.entity.WordEntity
import com.example.dictionary.domen.repository.word.RepositoryWordsLearn
import com.example.dictionary.domen.usecase_word.UseCaseWordsLearn
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class UseCaseWordsLearnImplament @Inject constructor(private var repository: RepositoryWordsLearn) : UseCaseWordsLearn {
    override fun getWordsList(id: Long): Flow<Responce<List<DataWord>>> = flow {
        try {
            emit(Responce.Loading(true))
            val list = repository.getWordsList(id).changeWordEntityListToDataWord(isActiveFirst = true, isActiveSecond = true)
            emit(Responce.Success(list))
            if (list.isEmpty())
                emit(Responce.Error("List is empty"))
        } catch (e: Exception) {
            emit(Responce.Error(e.message.toString()))
        }
    }

    override fun updateData(newdata: DataWord, oldData: DataWord): Flow<Responce<List<DataWord>>> = flow {
        try {
            emit(Responce.Loading(true))
            if (newdata.wordFirst == oldData.wordFirst && newdata.wordSecond == oldData.wordSecond) {
                emit(Responce.Message("Data does not change"))
            } else {
                val data = WordEntity(oldData.id, newdata.wordFirst, newdata.wordSecond, oldData.example, oldData.dictionaryId, oldData.learnCount)
                repository.update(data)
                val list = repository.getWordsList(oldData.dictionaryId).changeWordEntityListToDataWord(isActiveFirst = true, isActiveSecond = true)
                emit(Responce.Success(list))
                if (list.isEmpty())
                    emit(Responce.Error("List is empty"))
            }
        } catch (e: Exception) {
            emit(Responce.Error(e.message.toString()))

        }
    }

    override fun addNewData(newdata: DataWord, id: Long): Flow<Responce<List<DataWord>>> = flow {
        try {
            emit(Responce.Loading(true))
            Timber.d(newdata.wordFirst)
            Timber.d(newdata.wordSecond)
            Timber.d(newdata.dictionaryId.toString())
            Timber.d(id.toString())
            if (newdata.wordFirst == "" && newdata.wordSecond == "") {
                emit(Responce.Message("Data does not add"))
            } else {
                val data = WordEntity(0, newdata.wordFirst, newdata.wordSecond, "", id, 0)
                val wordId = repository.addNewWord(data)
                if (wordId > 0) {
                    val list = repository.getWordsList(id).changeWordEntityListToDataWord(isActiveFirst = true, isActiveSecond = true)
                    emit(Responce.Success(list))
                    if (list.isEmpty())
                        emit(Responce.Error("List is empty"))
                } else {
                    emit(Responce.Error("Data does not add"))
                }
            }
        } catch (e: Exception) {
            emit(Responce.Error(e.message.toString()))
        }
    }

    override fun deleteData(data: DataWord): Flow<Responce<List<DataWord>>> = flow {
        try {
            emit(Responce.Loading(true))
            val dt = WordEntity(data.id, data.wordFirst, data.wordSecond, data.example, data.dictionaryId, data.learnCount)
            val count = repository.delete(dt)
            if (count > 0) {
                val list = repository.getWordsList(data.dictionaryId).changeWordEntityListToDataWord(isActiveFirst = true, isActiveSecond = true)
                emit(Responce.Success(list))
                if (list.isEmpty())
                    emit(Responce.Error("List is empty"))
            } else {
                emit(Responce.Error("Something is wrong"))
            }

        } catch (e: Exception) {
            emit(Responce.Error(e.message.toString()))
        }
    }
}