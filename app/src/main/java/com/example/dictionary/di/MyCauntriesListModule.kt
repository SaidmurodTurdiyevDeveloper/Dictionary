package com.example.dictionary.di

import com.example.dictionary.utils.other.MyCountries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyCauntriesListModule {
    @Provides
    @Singleton
    fun provideMyCountries(): MyCountries = MyCountries()
}