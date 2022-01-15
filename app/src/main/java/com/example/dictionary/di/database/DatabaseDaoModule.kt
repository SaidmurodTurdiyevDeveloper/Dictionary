package com.example.dictionary.di.database

import com.example.dictionary.data.source.local.room.MyDataBase
import com.example.dictionary.data.source.local.room.dao.SplashDatabaseDao
import com.example.dictionary.data.source.local.room.dao.WordRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.ArxiveRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.DictionaryItemRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.MainRoomDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseDaoModule {

    @Provides
    fun getDatabaseDaoDictionaryMain(dataBase: MyDataBase): MainRoomDatabaseDao =
        dataBase.getDictionaryDataBaseForMain()

    @Provides
    fun getDatabaseDaoDictionaryArxive(dataBase: MyDataBase): ArxiveRoomDatabaseDao =
        dataBase.getDictionaryDataBaseForArxive()

    @Provides
    fun getDatabaseDaoDictionaryForItems(dataBase: MyDataBase): WordRoomDatabaseDao =
        dataBase.getWordsDataBase()

    @Provides
    fun getSplashRoomDatabaseDao(dataBase: MyDataBase): SplashDatabaseDao = dataBase.getSplashDao()

    @Provides
    fun getDictionaryItemDao(dataBase: MyDataBase): DictionaryItemRoomDatabaseDao = dataBase.getDictionaryItemDao()
}