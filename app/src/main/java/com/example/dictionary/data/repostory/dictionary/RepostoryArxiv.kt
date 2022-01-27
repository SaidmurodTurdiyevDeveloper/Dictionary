package com.example.dictionary.data.repostory.dictionary

import com.example.dictionary.contracts.dictionary.ContractArchive
import com.example.dictionary.data.source.local.room.dao.dictionaries.ArchiveRoomDatabaseDao
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import javax.inject.Inject

class RepostoryArxiv @Inject constructor(
    private var database: ArchiveRoomDatabaseDao,
) : ContractArchive.Model {

    override suspend fun getArchiveList(): List<DictionaryEntity> = database.getArxiveList()

    override suspend fun delete(data: DictionaryEntity): Int = database.delete(data)

    override suspend fun deleteAll(list: List<DictionaryEntity>): Int = database.deleteAll(list)

    override suspend fun returnToActive(data: DictionaryEntity) = database.update(data)

    override suspend fun getItemCount(): Int = database.getSize()
}