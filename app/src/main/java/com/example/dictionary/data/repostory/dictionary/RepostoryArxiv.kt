package com.example.dictionary.data.repostory.dictionary

import com.example.dictionary.contracts.dictionary.ContractArchive
import com.example.dictionary.data.source.local.room.dao.dictionaries.ArxiveDao
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import javax.inject.Inject

class RepostoryArxiv @Inject constructor(
    private var database: ArxiveDao,
) : ContractArchive.Model {
    override suspend fun getAllRemovedItems(): List<DictionaryEntity> = database.getAllDeletedItem()

    override suspend fun delete(data: DictionaryEntity): Boolean {
        return 0 < database.delete(data)
    }

    override suspend fun deleteAll(list: List<DictionaryEntity>): Boolean {
        return 0 < database.deleteAll(list)
    }

    override suspend fun update(data: DictionaryEntity) {
        database.update(data)
    }

    override suspend fun getItemCount(): Int = database.getSize()
}