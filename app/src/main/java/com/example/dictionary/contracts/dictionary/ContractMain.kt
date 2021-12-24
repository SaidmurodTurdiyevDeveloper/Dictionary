package com.example.dictionary.contracts.dictionary

import com.example.dictionary.data.source.local.room.entity.DictionaryEntity

interface ContractMain {
    interface Model {
       suspend fun getLearnCount(): Long
       suspend fun add(data: DictionaryEntity)
       suspend fun edit(data: DictionaryEntity)
       suspend fun delete(data: DictionaryEntity)
       suspend fun deleteAll()

       suspend fun getList(): List<DictionaryEntity>
       fun getDayNight(): Boolean
       fun setDayNight(cond: Boolean)
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

        fun edit(oldData: DictionaryEntity)
        fun update(newData: DictionaryEntity)

        fun select(position: Int)
        fun onceCheck(position: Int)
        fun deleteAll()
        fun checkAll(cond: Boolean)
        fun cancelSelected()


        fun darkLightClick()

        fun add()
        fun addItem(data: DictionaryEntity)

        fun openHome()
        fun openArxive()
        fun openSetting()
        fun openGame()
        fun openChangeLanguage()
        fun openInfo()
    }
}