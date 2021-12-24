package com.example.dictionary.di.database

import com.example.dictionary.data.source.local.room.MyDataBase
import com.example.dictionary.data.source.local.room.dao.WordDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.ArxiveDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.DictionaryItemDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.MainDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseDaoModule {

    @Provides
    fun getDatabaseDaoWord(dataBase: MyDataBase): WordDao = dataBase.getWordsDataBase()

    @Provides
    fun getDatabaseDaoDictionaryMain(dataBase: MyDataBase): MainDao =
        dataBase.getDictionaryDataBaseForMain()

    @Provides
    fun getDatabaseDaoDictionaryArxive(dataBase: MyDataBase): ArxiveDao =
        dataBase.getDictionaryDataBaseForArxive()

    @Provides
    fun getDatabaseDaoDictionaryForItems(dataBase: MyDataBase): DictionaryItemDao =
        dataBase.getDictionoryItemDatabase()
}