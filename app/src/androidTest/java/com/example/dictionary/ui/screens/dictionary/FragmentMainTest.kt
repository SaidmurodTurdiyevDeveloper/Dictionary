package com.example.dictionary.ui.screens.dictionary

import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class FragmentMainTest{
    @get:Rule
    var hilstRule=HiltAndroidRule(this)

    @Before
    fun setUp(){
        hilstRule.inject()
    }

    @
}