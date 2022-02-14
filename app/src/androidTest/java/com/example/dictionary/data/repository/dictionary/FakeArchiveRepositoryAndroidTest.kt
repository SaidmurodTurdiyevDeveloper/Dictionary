package com.example.dictionary.data.repository.dictionary

import com.example.dictionary.contracts.dictionary.ContractArchive
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeArchiveRepositoryAndroidTest : ContractArchive.Model {
    private var listArchive = ArrayList<DictionaryEntity>()
    override suspend fun getArchiveList(): List<DictionaryEntity> = listArchive

    fun addDictionary(data: DictionaryEntity) {
        listArchive.add(data)
    }

    override suspend fun delete(data: DictionaryEntity): Int {
        val size = listArchive.size
        val result = listArchive.remove(data)
        return if (result) size - listArchive.size else
            -1
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

    override fun getItemCount(): Flow<Int> = flow {
        emit(listArchive.size)
    }
}