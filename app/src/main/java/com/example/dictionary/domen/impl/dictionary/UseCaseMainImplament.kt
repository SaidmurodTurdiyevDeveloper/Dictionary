package com.example.dictionary.domen.impl.dictionary

import com.example.dictionary.contracts.dictionary.ContractMain
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.domen.usecase_dictionary.UseCaseMain
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UseCaseMainImplament @Inject constructor(private var repository: ContractMain.Model) : UseCaseMain {

    private var listAllDictionary: ArrayList<DictionaryEntity> = ArrayList()
    private var firstCountryId = repository.getLanguageOne()
    private var secondCountryId = repository.getLanguageTwo()
    private var EMPTY_LIST = "It couldn`t find any dictionary"

    override suspend fun getDictionaryList(): Flow<Responce<List<DictionaryEntity>>> = flow {
        try {
            emit(Responce.Loading(true))
            val list = repository.getActiveListOfDictionary(firstCountryId, secondCountryId)
            emit(Responce.Success(list))
            if (list.isEmpty())
                emit(Responce.Message(EMPTY_LIST))
            emit(Responce.Loading(false))
        } catch (e: Exception) {
            emit(Responce.Loading(false))
            emit(Responce.Error(e.message.toString()))
        }
    }

    override suspend fun getCountLearnedWords(): Flow<String> = flow {
        val count = repository.getCountOfWordsWhichLearned()
        if (count > 1000) {
            val thausand = (count.toFloat() / 1000F).toInt()
            val ten = count.minus(thausand * 1000F).div(10F).toInt()
            when {
                thausand >= 1000 -> {
                    val million = thausand.div(1000F).toInt()
                    if (million > 1000) {
                        emit("More")
                    } else
                        emit(million.toString() + "M")
                }
                thausand < 1000 -> {
                    if (ten % 10 == 0)
                        emit(thausand.toString() + "." + (ten / 10).toString() + "K")
                    else emit(thausand.toString() + "." + ten.toString() + "K")
                }
            }
        } else emit(count.toString())
    }

    override suspend fun addDictionary(data: DictionaryEntity): Flow<Responce<List<DictionaryEntity>>> = flow {
        try {
            emit(Responce.Loading(true))
            data.languageIdOne = firstCountryId
            data.languageIdTwo = secondCountryId
            val result = repository.addNewDictionary(data)
            val list = repository.getActiveListOfDictionary(firstCountryId, secondCountryId)
            emit(Responce.Success(list))
            if (result <= -1) emit(Responce.Message("This does not add"))
            emit(Responce.Loading(false))
        } catch (e: Exception) {
            emit(Responce.Loading(false))
            emit(Responce.Error(e.message.toString()))
        }
    }

    override suspend fun removeListDictionary(list: List<DictionaryEntity>): Flow<Responce<List<DictionaryEntity>>> = flow {
        try {
            emit(Responce.Loading(true))
            list.forEach {
                it.isDelete = 0
                repository.updateDictionary(it)
            }
            val newList = repository.getActiveListOfDictionary(firstCountryId, secondCountryId)
            emit(Responce.Success(newList))
            if (newList.isEmpty())
                emit(Responce.Message(EMPTY_LIST))
            emit(Responce.Loading(false))
        } catch (e: Exception) {
            emit(Responce.Loading(false))
            emit(Responce.Error(e.message.toString()))
        }
    }

    override suspend fun removeItemDictionary(data: DictionaryEntity): Flow<Responce<List<DictionaryEntity>>> = flow {
        try {
            emit(Responce.Loading(true))
            data.isDelete = 0
            repository.updateDictionary(data)
            val newList = repository.getActiveListOfDictionary(firstCountryId, secondCountryId)
            emit(Responce.Success(newList))
            if (newList.isEmpty())
                emit(Responce.Message(EMPTY_LIST))
            emit(Responce.Loading(false))
        } catch (e: Exception) {
            emit(Responce.Loading(false))
            emit(Responce.Error(e.message.toString()))
        }
    }

    override suspend fun updateDictionary(oldData: DictionaryEntity, newData: DictionaryEntity): Flow<Responce<List<DictionaryEntity>>> = flow {
        try {
            emit(Responce.Loading(true))
            if (oldData.id != newData.id || oldData.languageIdOne != newData.languageIdOne || oldData.languageIdTwo != newData.languageIdTwo) {
                newData.id = oldData.id
                newData.languageIdOne = oldData.languageIdOne
                newData.languageIdTwo = oldData.languageIdTwo
            }
            if (oldData.name == newData.name && oldData.dataInfo == newData.dataInfo) emit(Responce.Message("This data is not changed")) else {
                repository.updateDictionary(newData)
                val list = repository.getActiveListOfDictionary(firstCountryId, secondCountryId)
                emit(Responce.Success(list))
            }
            emit(Responce.Loading(false))
        } catch (e: Exception) {
            emit(Responce.Loading(false))
            emit(Responce.Error(e.message.toString()))
        }
    }

    override suspend fun removeDictionaryActive(data: DictionaryEntity): Flow<Responce<List<DictionaryEntity>>> = flow {
        try {
            emit(Responce.Loading(true))
            data.isDelete = 1
            repository.encaseToArchive(data)
            val list = repository.getActiveListOfDictionary(firstCountryId, secondCountryId)
            emit(Responce.Success(list))
            if (list.isEmpty()) emit(Responce.Error(EMPTY_LIST))
        } catch (e: Exception) {
            emit(Responce.Loading(false))
            emit(Responce.Error(e.message.toString()))
        }
    }

    override suspend fun deleteSelectedList(): Flow<Responce<List<DictionaryEntity>>> = flow {
        try {
            emit(Responce.Loading(true))
            val list = listAllDictionary.filter {
                it.isSelect
            }
            list.forEach {
                it.isDelete = 1
                repository.encaseToArchive(it)
            }
            val newList = repository.getActiveListOfDictionary(firstCountryId, secondCountryId)
            if (newList.isEmpty()) emit(Responce.Error(EMPTY_LIST))
            emit(Responce.Success(newList))
            emit(Responce.Loading(false))
        } catch (e: Exception) {
            emit(Responce.Loading(false))
            emit(Responce.Error(e.message.toString()))
        }
    }

    override fun selectItem(data: DictionaryEntity): Flow<Responce<List<DictionaryEntity>>> = flow {
        try {
            if (listAllDictionary.isEmpty()) {
                listAllDictionary.addAll(repository.getActiveListOfDictionary(firstCountryId, secondCountryId))
                val position = getPosition(data)
                listAllDictionary[position].isSelect = true
            } else {
                val newData = with(data) { DictionaryEntity(id, name, dataInfo, learnPracent, languageIdOne, languageIdTwo, isDelete) }
                newData.isSelect = !data.isSelect
                val position = getPosition(data)
                listAllDictionary.set(position, newData)
            }
            emit(Responce.Success(listAllDictionary.toMutableList()))
        } catch (e: Exception) {
            emit(Responce.Error(e.message.toString()))
        }
    }

    override fun cancelSelect() {
        listAllDictionary.clear()
    }

    override fun selectAllItem(cond: Boolean): Flow<Responce<List<DictionaryEntity>>> = flow {
        try {
            emit(Responce.Loading(true))
            val newList = repository.getActiveListOfDictionary(firstCountryId, secondCountryId)
            newList.forEach {
                it.isSelect = cond
            }
            listAllDictionary.clear()
            listAllDictionary.addAll(newList)
            emit(Responce.Success(listAllDictionary.toMutableList()))
            emit(Responce.Loading(false))
        } catch (e: Exception) {
            emit(Responce.Loading(false))
            emit(Responce.Error(e.message.toString()))
        }
    }

    override suspend fun changeDayNight(): Flow<Boolean> = flow {
        val cond = !repository.getIsDayOrNight()
        repository.setDayOrNight(cond)
        emit(cond)
    }

    override fun selectedItemcount(): Int = listAllDictionary.filter { it.isSelect }.size

    override fun selectedItems(): List<DictionaryEntity> = listAllDictionary.filter { it.isSelect }

    private fun getPosition(data: DictionaryEntity): Int {
        for (i in 0 until listAllDictionary.size) if (listAllDictionary[i].id == data.id)
            return i
        return -1
    }
}