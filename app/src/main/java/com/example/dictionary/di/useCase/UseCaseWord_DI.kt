package com.example.dictionary.di.useCase

import com.example.dictionary.domen.usecase_word.UseCaseWordItem
import com.example.dictionary.domen.usecase_word.UseCaseWordList
import com.example.dictionary.domen.usecase_word.UseCaseWordsLearn
import com.example.dictionary.domen.usecase_word.UseCaseWordsPlay
import com.example.dictionary.domen.impl.word.UseCaseWordItemImplament
import com.example.dictionary.domen.impl.word.UseCaseWordListImplament
import com.example.dictionary.domen.impl.word.UseCaseWordsLearnImplament
import com.example.dictionary.domen.impl.word.UseCaseWordsPlayImplament
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseWord_DI {

    @Binds
    fun bindsUsecaseWordItem(useCase: UseCaseWordItemImplament): UseCaseWordItem

    @Binds
    fun bindsUseCaseWordList(useCase: UseCaseWordListImplament): UseCaseWordList

    @Binds
    fun bindsUseCaseWordsLearn(useCase: UseCaseWordsLearnImplament): UseCaseWordsLearn

    @Binds
    fun bindsUseCaseWordsPlay(useCase: UseCaseWordsPlayImplament): UseCaseWordsPlay

}