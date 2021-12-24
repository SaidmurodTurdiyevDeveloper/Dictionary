package com.example.dictionary.di.repostory

import com.example.dictionary.contracts.dictionary.ContractArxive
import com.example.dictionary.contracts.dictionary.ContractChooseLanguages
import com.example.dictionary.contracts.dictionary.ContractMain
import com.example.dictionary.contracts.mixed.ContractDictionaryItem
import com.example.dictionary.data.repostory.dictionary.RepostoryArxiv
import com.example.dictionary.data.repostory.dictionary.RepostoryChooseCountry
import com.example.dictionary.data.repostory.dictionary.RepostoryMain
import com.example.dictionary.data.repostory.mixed.RepostoryDictionaryItem
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ContractRepostoryImp {
    @Binds
    fun getMainRepastory(repostory: RepostoryMain): ContractMain.Model

    @Binds
    fun getDictionaryItemRepostory(repostory: RepostoryDictionaryItem): ContractDictionaryItem.Model

    @Binds
    fun getChooseCountryRepostory(repostoryChooseCountry: RepostoryChooseCountry): ContractChooseLanguages.Model

    @Binds
    fun getArxiveDictionary(repostory: RepostoryArxiv): ContractArxive.Model
}