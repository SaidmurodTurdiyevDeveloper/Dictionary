package com.example.dictionary.di

import android.content.Context
import androidx.room.Room
import com.example.dictionary.data.source.local.room.DictionaryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.testing.TestComponentData
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestRoomDatabaseModule {

    @Provides
    @Named("test_db")
    fun profideDatabase(@ApplicationContext context: Context) = Room
        .inMemoryDatabaseBuilder(context, DictionaryDatabase::class.java)
        .allowMainThreadQueries()
        .build()
}