package com.example.dictionary.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionary.data.model.DataSelected

@Entity(tableName = "Dictionaries", ignoredColumns = arrayOf("isSelect"))
data class DictionaryEntity(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var name: String,
    var dataInfo:String,
    var learnPracent: Int,
    var languageIdOne: Int,
    var languageIdTwo: Int,
    var isDelete:Int
) :DataSelected()