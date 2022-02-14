package com.example.dictionary.data.repository

import com.example.dictionary.contracts.ContractChooseLanguages
import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.utils.other.MyCountries

class FakeChooseLanguageRepositoryAndroidTest : ContractChooseLanguages.Model {
    private var countryList = MyCountries()
    private var firstCountry = 0
    private var secondCountry = 0
    private var isFirstTimeEnter = false

    override suspend fun getCountriesList(): List<DataCountry> = countryList.getCountries()

    override suspend fun setFirstCountryId(countryId: Int) {
        firstCountry = countryId
    }

    override suspend fun setSecondCountryId(countryId: Int) {
        secondCountry = countryId
    }

    override suspend fun getFirstCountryId(): Int = firstCountry

    override suspend fun getSecondCountryId(): Int = secondCountry

    override suspend fun setFirstTimeEnter() {
        isFirstTimeEnter = true
    }

    override suspend fun getThisFirstTimeEnter(): Boolean = isFirstTimeEnter
}