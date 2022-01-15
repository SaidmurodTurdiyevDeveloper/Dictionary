package com.example.dictionary.contracts.dictionary

import com.example.dictionary.data.source.local.room.entity.DictionaryEntity

interface ContractMain {
    interface Model {
       suspend fun getCountOfWordsWhichLearned(): Long
       suspend fun addNewDictionary(data: DictionaryEntity)
       suspend fun updateDictionary(data: DictionaryEntity)
       suspend fun encaseToArchive(data: DictionaryEntity)
       suspend fun encaseListtoArchive()

       suspend fun getActiveListOfDictionary(): List<DictionaryEntity>
       fun getIsDayOrNight(): Boolean
       fun setDayOrNight(cond: Boolean)
       suspend fun selectAll():List<DictionaryEntity>
       suspend fun cancelSelected()
       suspend fun check(position: Int):Boolean
       suspend fun addArrays():List<DictionaryEntity>
       suspend fun getSelectedArray():List<DictionaryEntity>
    }

    interface ViewModel {
        fun clickItem(id: Long)
        fun openItem(id:Long)

        fun remove(data: DictionaryEntity)
        fun delete(data: DictionaryEntity)

        fun update(oldData: DictionaryEntity)

        fun select(position: Int)
        fun onceCheck(position: Int)
        fun deleteAll()
        fun checkAll(cond: Boolean)
        fun cancelSelected()


        fun darkLightClick()

        fun add()

        fun openHome()
        fun openArxive()
        fun openSetting()
        fun openGame()
        fun openChangeLanguage()
        fun openInfo()
    }
}