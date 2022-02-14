package com.example.dictionary.di.repository

import com.example.dictionary.contracts.ContractChooseLanguages
import com.example.dictionary.contracts.ContractSplash
import com.example.dictionary.data.repostory.RepositoryGameImplament
import com.example.dictionary.data.repostory.RepositorySettingImplament
import com.example.dictionary.data.repostory.RepostoryChooseCountry
import com.example.dictionary.data.repostory.RepostorySplash
import com.example.dictionary.domen.repository.RepositoryGame
import com.example.dictionary.domen.repository.RepositorySetting
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface Repository_DI {
    @Binds
    fun bindRepositoryChoosLanguage(repostoryChooseCountry: RepostoryChooseCountry): ContractChooseLanguages.Model

    @Binds
    fun bindRepostorySplash(repostory: RepostorySplash): ContractSplash.Model

    @Binds
    fun bindsRepositoryGame(repository:RepositoryGameImplament):RepositoryGame

    @Binds
    fun bindsRepositorySetting(repository:RepositorySettingImplament):RepositorySetting
}