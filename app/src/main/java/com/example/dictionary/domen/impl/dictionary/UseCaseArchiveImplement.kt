package com.example.dictionary.domen.impl.dictionary

import com.example.dictionary.contracts.dictionary.ContractArchive
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.domen.usecase_dictionary.UseCaseArchive
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UseCaseArchiveImplement @Inject constructor(private var repository: ContractArchive.Model) : UseCaseArchive {
    private var ARCHIVE_IS_EMPTY = "Archive list is empty"
    override fun getAllArxiveList(): Flow<Responce<List<DictionaryEntity>>> = flow {
        try {
            emit(Responce.Loading(true))
            val list = repository.getArchiveList()
            emit(Responce.Success(list))
            if (list.isEmpty()) {
                delay(400)
                emit(Responce.Message(ARCHIVE_IS_EMPTY))
            }
            emit(Responce.Loading(false))
        } catch (e: Exception) {
            emit(Responce.Loading(false))
            emit(Responce.Error(e.message.toString()))
        }
    }

    override fun delete(data: DictionaryEntity): Flow<Responce<List<DictionaryEntity>>> = flow {
        try {
            emit(Responce.Loading(true))
            val result = repository.delete(data)
            if (result > 0) {
                val list = repository.getArchiveList()
                emit(Responce.Success(list))
                if (list.isEmpty()) {
                    delay(400)
                    emit(Responce.Message(ARCHIVE_IS_EMPTY))
                }
            } else emit(Responce.Error("This data can not delete"))
            emit(Responce.Loading(false))
        } catch (e: Exception) {
            emit(Responce.Loading(false))
            emit(Responce.Error(e.message.toString()))
        }
    }

    override fun update(data: DictionaryEntity): Flow<Responce<List<DictionaryEntity>>> = flow {
        try {
            emit(Responce.Loading(true))
            data.isDelete = 0
            repository.returnToActive(data)
            val list = repository.getArchiveList()
            emit(Responce.Success(list))
            if (list.isEmpty()) {
                delay(400)
                emit(Responce.Message(ARCHIVE_IS_EMPTY))
            }
            emit(Responce.Loading(false))
        } catch (e: Exception) {
            emit(Responce.Loading(false))
            emit(Responce.Error(e.message.toString()))
        }
    }

    override fun deleteAll(): Flow<Responce<List<DictionaryEntity>>> = flow {
        try {
            emit(Responce.Loading(true))
            val oldlist = repository.getArchiveList()
            repository.deleteAll(oldlist)
            val list = repository.getArchiveList()
            emit(Responce.Success(list))
            if (list.isEmpty()) {
                delay(400)
                emit(Responce.Message(ARCHIVE_IS_EMPTY))
            }
            emit(Responce.Loading(false))
        } catch (e: Exception) {
            emit(Responce.Loading(false))
            emit(Responce.Error(e.message.toString()))
        }
    }

    override fun getSize(): Flow<Int> = repository.getItemCount()

}