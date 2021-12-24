package com.example.dictionary.contracts.words

import androidx.lifecycle.LiveData
import com.example.dictionary.data.source.local.room.entity.WordEntity

interface ContractWordList {
    interface Model {
        suspend fun getlist(): List<WordEntity>
        suspend fun deleteAll()
        suspend fun check(Position: Int)
        suspend fun add(data: WordEntity)
    }

    interface View {
        fun loadList(list: List<WordEntity>)
        fun openitem(data: WordEntity)
        fun check()
        fun openAddDialog()
    }

    interface ViewModel {
        val openItem: LiveData<WordEntity>
        val checkItem: LiveData<WordEntity>
        val addItem: LiveData<WordEntity>
        fun openItem()
        fun checkItem()
        fun addItem()
    }
}