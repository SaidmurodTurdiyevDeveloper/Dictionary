package com.example.dictionary.di.database

import android.content.Context
import com.example.dictionary.data.source.local.shared.SharedDatabese
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalStoradge_Modele {
    @Provides
    @Singleton
    fun provideSharedDatabase(@ApplicationContext context: Context): SharedDatabese =
        SharedDatabese.getInstaces(context)

}