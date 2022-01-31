package com.example.dictionary.di.repository

import com.example.dictionary.contracts.dictionary.ContractArchive
import com.example.dictionary.contracts.dictionary.ContractDictionaryItem
import com.example.dictionary.contracts.dictionary.ContractMain
import com.example.dictionary.data.repostory.dictionary.*
import com.example.dictionary.domen.repository.dictionary.RepositoryDictionaryInfo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryDictionary_DI {
    @Binds
    fun bindRepastoryMain(repostory: RepostoryMain): ContractMain.Model

    @Binds
    fun bindRepositoryDictionaryItem(repostory: RepostoryDictionaryItem): ContractDictionaryItem.Model

    @Binds
    fun bindRepositoryArchive(repostory: RepostoryArxiv): ContractArchive.Model

    @Binds
    fun bindRepositoryDictionaryInfo(repository: RepositoryDictionaryInfoImplament): RepositoryDictionaryInfo
}