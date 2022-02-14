package com.example.dictionary.domen.impl.dictionary

import com.example.dictionary.R
import com.example.dictionary.contracts.dictionary.ContractDictionaryItem
import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.domen.usecase_dictionary.UseCaseItemDictionary
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UseCaseitemDictionaryImplament @Inject constructor(var repository: ContractDictionaryItem.Model) : UseCaseItemDictionary {

    override fun getDictionary(id: Long): Flow<Responce<DictionaryEntity>> = flow {
        try {
            emit(Responce.Loading(true))
            emit(Responce.Success(repository.getData(id)))
            emit(Responce.Loading(false))
        } catch (e: Exception) {
            emit(Responce.Loading(false))
            emit(Responce.Error("Data is not found"))
        }
    }

    override fun getDictionaryLearnCount(id: Long): Flow<Responce<String>> = flow {
        try {
            val caunt = repository.getWordsCount(id)
            val cauntLearn = repository.getLearnWordsCount(id)
            if (caunt >= cauntLearn) {
                val text = cauntLearn.toString() + "/" + caunt
                emit(Responce.Success(text))
            } else
                emit(Responce.Message("Something is wrong"))
        } catch (e: Exception) {
            emit(Responce.Error(e.message.toString()))
        }
    }

    override suspend fun getCountryWithId(position: Int): DataCountry {
        try {
            return repository.getCountryList()[position]
        } catch (e: Exception) {
            return DataCountry("Uzb", R.drawable.country_uzb)
        }
    }
}