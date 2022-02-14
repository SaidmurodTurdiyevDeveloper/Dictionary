package com.example.dictionary.data.model

import com.example.dictionary.data.source.local.room.entity.WordEntity

data class DataWord(
    var id: Long,
    var wordFirst: String,
    var wordSecond: String,
    var example: String,
    var dictionaryId: Long,
    var learnCount: Int,
    var isActiveFirst: Boolean,
    var isActiveSecond: Boolean
)

fun List<WordEntity>.changeWordEntityListToDataWord(isActiveFirst: Boolean, isActiveSecond: Boolean): List<DataWord> = this.map {
    DataWord(it.id, it.wordOne, it.wordTwo, it.example, it.dictionaryId, it.learnedCount, isActiveFirst, isActiveSecond)
}

fun ArrayList<DataWord>.replase(dataWord: DataWord) {
    for (i in 0 until size) {
        if (get(i).id == dataWord.id) {
            set(i, dataWord)
            return
        }
    }
}

fun DataWord.changeisActive(): DataWord = DataWord(id, wordFirst, wordSecond, example, dictionaryId, learnCount, !isActiveFirst, !isActiveSecond)
