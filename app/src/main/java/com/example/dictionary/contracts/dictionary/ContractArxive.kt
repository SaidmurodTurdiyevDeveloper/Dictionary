package com.example.dictionary.contracts.dictionary

import com.example.dictionary.data.source.local.room.entity.DictionaryEntity

interface ContractArxive {
    interface Model {
        suspend fun getAllRemovedItems(): List<DictionaryEntity>
        suspend fun delete(data: DictionaryEntity): Boolean
        suspend fun deleteAll()
        suspend fun update(data: DictionaryEntity)
        fun getItemCount(): Int
    }

    interface ViewModel {
        fun itemTouch(data: DictionaryEntity)
        fun delete(data: DictionaryEntity)
        fun update(data: DictionaryEntity)
        fun deleteAll()
        fun back()
        fun openDialogDeleteAll()
    }
}