package com.example.dictionary.di.database

import com.example.dictionary.data.source.local.room.DictionaryDatabase
import com.example.dictionary.data.source.local.room.dao.SplashRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.WordRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.ArchiveRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.DictionaryItemRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.MainRoomDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseDaoModule {

    @Provides
    @Singleton
    fun provideMainRoomDatabaseDao(dataBase: DictionaryDatabase): MainRoomDatabaseDao =
        dataBase.getMainDao()

    @Provides
    @Singleton
    fun provideArchiveRoomDatabaseDao(dataBase: DictionaryDatabase): ArchiveRoomDatabaseDao =
        dataBase.getArchiveDao()

    @Provides
    @Singleton
    fun provideWordRoomDatabaseaDao(dataBase: DictionaryDatabase): WordRoomDatabaseDao =
        dataBase.getWordsListDao()

    @Provides
    @Singleton
    fun provideSplashRoomDatabaseDao(dataBase: DictionaryDatabase): SplashRoomDatabaseDao = dataBase.getSplashDao()

    @Provides
    @Singleton
    fun provideDictionaryItemRoomDatabaseDao(dataBase: DictionaryDatabase): DictionaryItemRoomDatabaseDao = dataBase.getDictionaryItemDao()
}