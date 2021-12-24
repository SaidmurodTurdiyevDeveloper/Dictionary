package com.example.dictionary.data.repostory.dictionary

import com.example.dictionary.contracts.dictionary.ContractChooseLanguages
import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.data.source.local.shared.SharedDatabese
import com.example.dictionary.utils.MyCountries
import com.example.dictionary.utils.MyStringObjects
import javax.inject.Inject

class RepostoryChooseCountry @Inject constructor(
    private val listCountry: MyCountries,
    private val storadge: SharedDatabese
) : ContractChooseLanguages.Model {

    override suspend fun getCountryList(): List<DataCountry> = listCountry.getCountries()


    override suspend fun setCountryOne(countryId: Int) {
        storadge.setIntData(MyStringObjects.LANGUAGE_ONE, countryId)
    }

    override suspend fun setCountryTwo(countryId: Int) {
        storadge.setIntData(MyStringObjects.LANGUAGE_TWO, countryId)
    }

    override suspend fun getCountryOne(): Int = storadge.getIntData(MyStringObjects.LANGUAGE_ONE)

    override suspend fun getCountryTwo(): Int = storadge.getIntData(MyStringObjects.LANGUAGE_TWO)

    override suspend fun setFirstEnter() {
        if (!storadge.getBoolenData(MyStringObjects.FIRST_ENTER_MODE))
            storadge.setBoolenData(MyStringObjects.FIRST_ENTER_MODE, true)
    }

    override suspend fun getIsFirstCountry(): Boolean {
        return storadge.getBoolenData(MyStringObjects.FIRST_ENTER_MODE)
    }
}