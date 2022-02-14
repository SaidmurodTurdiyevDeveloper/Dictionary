package com.example.dictionary.domen.impl

import com.example.dictionary.contracts.ContractSplash
import com.example.dictionary.domen.UseCaseSplash
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UseCaseSplashImplament @Inject constructor(private var repostory: ContractSplash.Model) : UseCaseSplash {
    override suspend fun init(): Flow<Responce<Boolean>> = flow {
        try {
            emit(Responce.Loading(true))
            repostory.deleteAllEmpty(repostory.getEmptyWord())
            emit(Responce.Success(repostory.getIsfFirst()))
            emit(Responce.Loading(false))
        } catch (e: Exception) {
            emit(Responce.Loading(false))
            emit(Responce.Error(e.message.toString()))
        }
    }
}