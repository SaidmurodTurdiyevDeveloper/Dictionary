package com.example.dictionary.contracts.dictionary

import com.example.dictionary.data.source.local.room.entity.DictionaryEntity

interface ContractMain {
    interface Model {
        suspend fun getCountOfWordsWhichLearned(): Long
        suspend fun addNewDictionary(data: DictionaryEntity): Long
        suspend fun updateDictionary(data: DictionaryEntity)
        suspend fun encaseToArchive(data: DictionaryEntity)
        suspend fun getActiveListOfDictionary(firstId: Int, secondId: Int): List<DictionaryEntity>
        fun getIsDayOrNight(): Boolean
        fun setDayOrNight(cond: Boolean)
        fun getLanguageOne():Int
        fun getLanguageTwo():Int
    }

    interface ViewModel {
        fun clickItem(data: DictionaryEntity)
        fun loadData()
        fun loadCountLearnedWord()
        fun delete(data: DictionaryEntity)
        fun update(oldData: DictionaryEntity)
        fun deleteAll()
        fun checkAll()
        fun cancelSelected()
        fun dayNightClick()
        fun add()

        fun openDictionaryItem(id:Long)
        fun openHome()
        fun openArxive()
        fun openSetting()
        fun openGame()
        fun openChangeLanguage()
        fun openAppInfo()
        fun openAnotherActionBar()
    }
}