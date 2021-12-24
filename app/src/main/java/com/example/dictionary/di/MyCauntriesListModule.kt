package com.example.dictionary.di

import com.example.dictionary.utils.MyCountries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MyCauntriesListModule {
    @Provides
    fun getMyCountries():MyCountries=MyCountries()
}