package com.example.dictionary.di.database

import android.content.Context
import com.example.dictionary.data.source.local.room.MyDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MyDatabaseModule {
    @Provides
    fun getDatabase(@ApplicationContext context: Context): MyDataBase =
        MyDataBase.getDatabase(context)
}