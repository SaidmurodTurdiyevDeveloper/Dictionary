package com.example.dictionary.di.useCase

import com.example.dictionary.domen.usecase_dictionary.UseCaseArchive
import com.example.dictionary.domen.usecase_dictionary.UseCaseDictionaryInfo
import com.example.dictionary.domen.usecase_dictionary.UseCaseItemDictionary
import com.example.dictionary.domen.usecase_dictionary.UseCaseMain
import com.example.dictionary.domen.impl.dictionary.UseCaseArchiveImplement
import com.example.dictionary.domen.impl.dictionary.UseCaseDictionaryInfoImplament
import com.example.dictionary.domen.impl.dictionary.UseCaseMainImplament
import com.example.dictionary.domen.impl.dictionary.UseCaseitemDictionaryImplament
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseDictionary_DI {

    @Binds
    fun bindUseCaseDictionaryItem(useCase: UseCaseitemDictionaryImplament): UseCaseItemDictionary

    @Binds
    fun bindUseCaseArchive(usecase: UseCaseArchiveImplement): UseCaseArchive

    @Binds
    fun bindUseCaseMain(useCaseMainImplament: UseCaseMainImplament): UseCaseMain

    @Binds
    fun bindsUseCaseDictionaryInfo(usecase: UseCaseDictionaryInfoImplament): UseCaseDictionaryInfo
}