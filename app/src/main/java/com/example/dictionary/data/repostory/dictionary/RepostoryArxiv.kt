package com.example.dictionary.data.repostory.dictionary

import com.example.dictionary.contracts.dictionary.ContractArxive
import com.example.dictionary.data.source.local.room.dao.dictionaries.ArxiveDao
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import javax.inject.Inject

class RepostoryArxiv @Inject constructor(
    private var database: ArxiveDao,
) : ContractArxive.Model {
    var count = 0;
    override suspend fun getAllRemovedItems(): List<DictionaryEntity> {
        val list = database.getAllDeletedItem()
        count=list.size
        return list
    }

    override suspend fun delete(data: DictionaryEntity): Boolean {
        return 0 < database.delete(data)
    }

    override suspend fun deleteAll() {
        val ls = getAllRemovedItems()
        database.deleteAll(ls)
    }

    override suspend fun update(data: DictionaryEntity) {
        data.isDelete = 0
        database.update(data)
    }

    override  fun getItemCount(): Int = count
}