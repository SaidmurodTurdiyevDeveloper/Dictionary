package com.example.dictionary.contracts.dictionary

import com.example.dictionary.data.model.DataCountry

interface ContractChooseLanguages {
    interface Model {
        suspend fun getCountryList():List<DataCountry>
        suspend fun setCountryOne(countryId:Int)
        suspend fun setCountryTwo(countryId: Int)
        suspend fun getCountryOne():Int
        suspend fun getCountryTwo():Int
        suspend fun setFirstEnter()
        suspend fun getIsFirstCountry():Boolean
    }
    interface ViewModel{
        fun clickOneCountry(countryId: Int)
        fun clickTwoCountry(countryId: Int)
        fun done()
    }
}