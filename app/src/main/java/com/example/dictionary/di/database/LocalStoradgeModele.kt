package com.example.dictionary.di.database

import android.content.Context
import com.example.dictionary.data.source.local.shared.SharedDatabese
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class LocalStoradgeModele {
    @Provides
    fun getLocalStoradge(@ApplicationContext context: Context): SharedDatabese =
        SharedDatabese.getInstaces(context)

}