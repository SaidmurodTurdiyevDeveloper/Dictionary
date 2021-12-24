package com.example.dictionary.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictionary.data.source.local.room.dao.WordDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.ArxiveDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.DictionaryItemDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.MainDao
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.data.source.local.room.entity.WordEntity

@Database(entities = [DictionaryEntity::class, WordEntity::class], version = 1)
abstract class MyDataBase : RoomDatabase() {
    abstract fun getDictionaryDataBaseForMain(): MainDao

    abstract fun getDictionaryDataBaseForArxive(): ArxiveDao

    abstract fun getDictionoryItemDatabase():DictionaryItemDao

    abstract fun getWordsDataBase(): WordDao

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