package com.example.dictionary.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictionary.data.source.local.room.dao.SplashDatabaseDao
import com.example.dictionary.data.source.local.room.dao.WordRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.ArxiveRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.DictionaryItemRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.MainRoomDatabaseDao
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.data.source.local.room.entity.WordEntity

@Database(entities = [DictionaryEntity::class, WordEntity::class], version = 1)
abstract class MyDataBase : RoomDatabase() {
    abstract fun getDictionaryDataBaseForMain(): MainRoomDatabaseDao
    abstract fun getDictionaryDataBaseForArxive(): ArxiveRoomDatabaseDao
    abstract fun getWordsDataBase(): WordRoomDatabaseDao
    abstract fun getSplashDao(): SplashDatabaseDao
    abstract fun getDictionaryItemDao(): DictionaryItemRoomDatabaseDao

    companion object {
        @Volatile
        lateinit var INSTANCE: MyDataBase
        fun getDatabase(context: Context): MyDataBase {
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDataBase::class.java,
                    "app_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}