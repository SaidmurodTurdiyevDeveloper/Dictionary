package com.example.dictionary.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dictionary.data.source.local.room.dao.SplashRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.WordRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.ArchiveRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.DictionaryItemRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.MainRoomDatabaseDao
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.data.source.local.room.entity.WordEntity

@Database(entities = [DictionaryEntity::class, WordEntity::class], version = 1)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun getMainDao(): MainRoomDatabaseDao
    abstract fun getArchiveDao(): ArchiveRoomDatabaseDao
    abstract fun getWordsListDao(): WordRoomDatabaseDao
    abstract fun getSplashDao(): SplashRoomDatabaseDao
    abstract fun getDictionaryItemDao(): DictionaryItemRoomDatabaseDao
}