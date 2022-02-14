package com.example.dictionary.data.repository.dictionary

import com.example.dictionary.contracts.dictionary.ContractArchive
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import kotlinx.coroutines.flow.Flow

class FakeArchiveRepository : ContractArchive.Model {
    private var listArchive = ArrayList<DictionaryEntity>()
    override suspend fun getArchiveList(): List<DictionaryEntity> = listArchive

    fun addDictionary(data: DictionaryEntity) {
        listArchive.add(data)
    }

    override suspend fun delete(data: DictionaryEntity): Int {
        val size = listArchive.size
        val result = listArchive.remove(data)
        if (result) return size - listArchive.size else
            return -1
    }

    override suspend fun deleteAll(list: List<DictionaryEntity>): Int {
        val oldCaunt = listArchive.size
        listArchive.removeAll(list)
        val newCaunt = listArchive.size
        return oldCaunt - newCaunt
    }

    override suspend fun returnToActive(data: DictionaryEntity) {
        listArchive.remove(data)
    }

    override fun getItemCount(): Flow<Int> {
        TODO("Not yet implemented")
    }

}