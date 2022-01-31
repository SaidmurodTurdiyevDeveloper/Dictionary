package com.example.dictionary.di.repository

import com.example.dictionary.data.repostory.word.RepositoryWordItemImplament
import com.example.dictionary.data.repostory.word.RepositoryWordLearnImplament
import com.example.dictionary.data.repostory.word.RepositoryWordListImplament
import com.example.dictionary.data.repostory.word.RepositoryWordPlayImplament
import com.example.dictionary.domen.repository.word.RepositoryWordItem
import com.example.dictionary.domen.repository.word.RepositoryWordsLearn
import com.example.dictionary.domen.repository.word.RepositoryWordsList
import com.example.dictionary.domen.repository.word.RepositoryWordsPlay
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryWord_DI {
    @Binds
    fun bindRepositoryWordsItem(repository: RepositoryWordItemImplament): RepositoryWordItem

    @Binds
    fun bindsRepositoryWordsLearn(repostory: RepositoryWordLearnImplament): RepositoryWordsLearn

    @Binds
    fun bindsRepositoryWordsList(repository: RepositoryWordListImplament): RepositoryWordsList

    @Binds
    fun bindsRepositoryWordsPlay(repository: RepositoryWordPlayImplament): RepositoryWordsPlay
}