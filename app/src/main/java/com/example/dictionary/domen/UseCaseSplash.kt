package com.example.dictionary.domen

import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow

interface UseCaseSplash {
    suspend fun init(): Flow<Responce<Boolean>>
}