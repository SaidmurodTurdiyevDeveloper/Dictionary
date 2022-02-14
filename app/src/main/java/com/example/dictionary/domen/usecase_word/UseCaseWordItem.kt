package com.example.dictionary.domen.usecase_word

import com.example.dictionary.data.source.local.room.entity.WordEntity
import com.example.dictionary.utils.other.Responce
import kotlinx.coroutines.flow.Flow

interface UseCaseWordItem {
    fun getWord(id: Long): Flow<Responce<WordEntity>>
    fun update(data: WordEntity, text: String): Flow<Responce<WordEntity>>
}