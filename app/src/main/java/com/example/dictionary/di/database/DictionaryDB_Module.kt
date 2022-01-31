package com.example.dictionary.di.database

import android.content.Context
import androidx.room.Room
import com.example.dictionary.data.source.local.room.DictionaryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DictionaryDB_Module {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DictionaryDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            DictionaryDatabase::class.java,
            "dictionary_db")
            .build()
}