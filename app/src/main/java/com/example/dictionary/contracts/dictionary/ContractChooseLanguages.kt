package com.example.dictionary.contracts.dictionary

import com.example.dictionary.data.model.DataCountry

interface ContractChooseLanguages {
    interface Model {
        suspend fun getCountriesList():List<DataCountry>
        suspend fun setFirstCountryId(countryId:Int)
        suspend fun setSecondCountryId(countryId: Int)
        suspend fun getFirstCountryId():Int
        suspend fun getSecondCountryId():Int
        suspend fun setFirstTimeEnter()
        suspend fun getThisFirstTimeEnter():Boolean
    }
    interface ViewModel{
        fun clickFirstCountry(countryId: Int)
        fun clickSecondCountry(countryId: Int)
        fun done()
    }
}