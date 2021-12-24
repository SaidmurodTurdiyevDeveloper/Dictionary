package com.example.dictionary.utils

import com.example.dictionary.R
import com.example.dictionary.data.model.DataCountry

class MyCountries {
    private var list = ArrayList<DataCountry>()

    init {
        list.clear()
        list.add(DataCountry("Uzbek", R.drawable.country_uzb))
        list.add(DataCountry("English", R.drawable.country_england_usa_mix))
        list.add(DataCountry("Arabic", R.drawable.country_arabia))
        list.add(DataCountry("Chinese", R.drawable.country_china))
        list.add(DataCountry("French", R.drawable.country_france))
        list.add(DataCountry("German", R.drawable.country_german))
        list.add(DataCountry("Italian", R.drawable.country_italy))
        list.add(DataCountry("Japanese", R.drawable.country_japan))
        list.add(DataCountry("Kazakh", R.drawable.country_kazakistan))
        list.add(DataCountry("Korean", R.drawable.country_korea))
        list.add(DataCountry("Portuguese", R.drawable.country_portugal))
        list.add(DataCountry("Russian", R.drawable.country_russion))
        list.add(DataCountry("Spanish", R.drawable.country_spain))
        list.add(DataCountry("Turkish", R.drawable.country_turkey))
    }

    fun getCountries(): List<DataCountry> {
        return list
    }
}