package com.example.dictionary.contracts.dictionary

import com.example.dictionary.data.source.local.room.entity.DictionaryEntity

interface ContractArchive {
    interface Model {
        suspend fun getArchiveList(): List<DictionaryEntity>
        suspend fun delete(data: DictionaryEntity): Int
        suspend fun deleteAll(list: List<DictionaryEntity>): Int
        suspend fun returnToActive(data: DictionaryEntity)
        suspend fun getItemCount(): Int
    }

    interface ViewModel {
        fun itemTouch(data: DictionaryEntity)
        fun delete(data: DictionaryEntity)
        fun returnToActive(data: DictionaryEntity)
        fun clearAll()
        fun back()
        fun getArchiveList()
    }
}