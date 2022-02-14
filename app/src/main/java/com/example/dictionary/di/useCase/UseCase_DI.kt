package com.example.dictionary.di.useCase

import com.example.dictionary.domen.UseCaseGame
import com.example.dictionary.domen.UseCaseSetting
import com.example.dictionary.domen.UseCaseSplash
import com.example.dictionary.domen.impl.UseCaseGameImplament
import com.example.dictionary.domen.impl.UseCaseSettingImplament
import com.example.dictionary.domen.impl.UseCaseSplashImplament
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCase_DI {
    @Binds
    fun bindUseCaseSplash(usecase: UseCaseSplashImplament): UseCaseSplash

    @Binds
    fun bindsUseCaseGame(usecase: UseCaseGameImplament): UseCaseGame

    @Binds
    fun bindsUseCaseSetting(usecase: UseCaseSettingImplament): UseCaseSetting
}