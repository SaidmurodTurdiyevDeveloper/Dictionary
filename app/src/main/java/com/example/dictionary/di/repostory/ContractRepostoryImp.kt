package com.example.dictionary.di.repostory

import com.example.dictionary.contracts.ContractSplash
import com.example.dictionary.contracts.dictionary.ContractArchive
import com.example.dictionary.contracts.dictionary.ContractChooseLanguages
import com.example.dictionary.contracts.dictionary.ContractMain
import com.example.dictionary.contracts.dictionary.ContractDictionaryItem
import com.example.dictionary.data.repostory.RepostorySplash
import com.example.dictionary.data.repostory.dictionary.RepostoryArxiv
import com.example.dictionary.data.repostory.dictionary.RepostoryChooseCountry
import com.example.dictionary.data.repostory.dictionary.RepostoryMain
import com.example.dictionary.data.repostory.dictionary.RepostoryDictionaryItem
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ContractRepostoryImp {
    @Binds
    fun bindRepastoryMain(repostory: RepostoryMain): ContractMain.Model

    @Binds
    fun bindRepositoryDictionaryItem(repostory: RepostoryDictionaryItem): ContractDictionaryItem.Model

    @Binds
    fun bindRepositoryChoosLanguage(repostoryChooseCountry: RepostoryChooseCountry): ContractChooseLanguages.Model

    @Binds
    fun bindRepositoryArchive(repostory: RepostoryArxiv): ContractArchive.Model

    @Binds
    fun bindRepostorySplash(repostory: RepostorySplash): ContractSplash.Model
}