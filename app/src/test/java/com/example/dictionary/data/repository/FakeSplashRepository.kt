package com.example.dictionary.data.repository

import com.example.dictionary.contracts.ContractSplash
import com.example.dictionary.data.source.local.room.entity.WordEntity

class FakeSplashRepository : ContractSplash.Model {
    private var existList = ArrayList<WordEntity>()
    private var isFirst = false

    init {
        val word = WordEntity(1, "Hello", "Salom", "Hello World", 1, 5)
        val word1 = WordEntity(2, "Good", "Yaxshi", "Good job", 1, 3)
        val word2 = WordEntity(3, "Perfect", "Mukammal", "it is perfect", 1, 4)
        existList.add(word)
        existList.add(word1)
        existList.add(word2)
    }

    fun setIsfirst(cond: Boolean) {
        isFirst = cond
    }

    override suspend fun getIsfFirst(): Boolean = isFirst

    override suspend fun getEmptyWord(): List<WordEntity> = existList

    override suspend fun deleteAllEmpty(list: List<WordEntity>): Int {
        val firstCount = existList.size
        existList.removeAll(list)
        return firstCount - existList.size
    }
}