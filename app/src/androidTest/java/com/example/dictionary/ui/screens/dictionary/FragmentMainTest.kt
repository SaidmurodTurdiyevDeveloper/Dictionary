package com.example.dictionary.ui.screens.dictionary

import android.view.Gravity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.DrawerActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.dictionary.R
import com.example.dictionary.launchFragmentInHiltContainer
import com.example.dictionary.ui.screens.zzz.DictionaryFragmentFactoryAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class FragmentMainTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hilstRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: DictionaryFragmentFactoryAndroidTest

    @Before
    fun setUp() {
        hilstRule.inject()
    }

    @Test
    fun openMenuTest() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<FragmentMain>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.drawer_layout_home)).perform(open(Gravity.START))
        onView(withId(R.id.drawer_layout_home)).perform(close(Gravity.START))
        onView(withId(R.id.menuBtnHome)).perform(click())
        onView(withId(R.id.closeMenuButtonHome)).perform(click())
    }
}