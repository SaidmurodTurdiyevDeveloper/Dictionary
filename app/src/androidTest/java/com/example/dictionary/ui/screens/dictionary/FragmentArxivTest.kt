package com.example.dictionary.ui.screens.dictionary

import android.annotation.SuppressLint
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.example.dictionary.R
import com.example.dictionary.getOrAwaitValue
import com.example.dictionary.launchFragmentInHiltContainer
import com.example.dictionary.ui.adapter.AdapterDictionary
import com.example.dictionary.ui.screens.zzz.DictionaryFragmentFactoryAndroidTest
import com.example.dictionary.ui.viewModel.impl.dictionary.ViewModelArchive
import com.google.common.truth.Truth.assertThat
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
class FragmentArxivTest {
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
    fun loadingListToRecyclerView() {
        val navCantroller = mock(NavController::class.java)
        var viewModel: ViewModelArchive? = null
        launchFragmentInHiltContainer<FragmentArchive>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navCantroller)
            viewModel = testDefViewModel
        }
        assertThat(viewModel?.loadAllDataLiveData?.getOrAwaitValue()).isNotNull()
        assertThat(viewModel?.loadAllDataLiveData?.getOrAwaitValue()?.peekContent()).isNotEmpty()
        assertThat(viewModel?.loadAllDataLiveData?.getOrAwaitValue()?.peekContent()?.size).isEqualTo(1)
        onView(withId(R.id.list)).check(matches(isDisplayed()))
    }

    @SuppressLint("CheckResult")
    @Test
    fun deleteInArchiveDialog() {
        val navCantroller = mock(NavController::class.java)
        var viewModel: ViewModelArchive? = null
        launchFragmentInHiltContainer<FragmentArchive>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navCantroller)
            viewModel = testDefViewModel
        }
        assertThat(viewModel?.loadAllDataLiveData?.value?.peekContent()).isNotEmpty()
        onView(withId(R.id.def_dialog)).check(doesNotExist())
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition<AdapterDictionary.ViewHolder>(0, click()))
        onView(withId(R.id.def_dialog)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_right)).perform(click())
        assertThat(viewModel?.loadAllDataLiveData?.value?.peekContent()).isEmpty()
    }

    @SuppressLint("CheckResult")
    @Test
    fun returnDatatoActiveDialog() {
        val navCantroller = mock(NavController::class.java)
        var viewModel: ViewModelArchive? = null
        launchFragmentInHiltContainer<FragmentArchive>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navCantroller)
            viewModel = testDefViewModel
        }
        assertThat(viewModel?.loadAllDataLiveData?.value?.peekContent()).isNotEmpty()
        onView(withId(R.id.def_dialog)).check(doesNotExist())
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition<AdapterDictionary.ViewHolder>(0, click()))
        onView(withId(R.id.def_dialog)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_left)).perform(click())
        assertThat(viewModel?.loadAllDataLiveData?.value?.peekContent()).isEmpty()
    }

    @SuppressLint("CheckResult")
    @Test
    fun clearArchive() {
        val navCantroller = mock(NavController::class.java)
        var viewModel: ViewModelArchive? = null
        launchFragmentInHiltContainer<FragmentArchive>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navCantroller)
            viewModel = testDefViewModel
        }
        onView(withId(R.id.btn_menu)).perform(click())
        onView(withId(R.id.menu_item_clear_all)).check(matches(isDisplayed()))
        onView(withId(R.id.menu_item_clear_all)).perform(click())
        onView(withId(R.id.tvError)).check(matches(withText("Archive list is empty")))
    }

    @Test
    fun back_button() {
        val navController = mock(NavController::class.java)
        var viewModelArchive: ViewModelArchive? = null
        launchFragmentInHiltContainer<FragmentArchive>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModelArchive = testDefViewModel
        }
        onView(withId(R.id.def_archive_layout)).check(matches(isDisplayed()))
        assertThat(viewModelArchive?.backLiveData?.value?.peekContent()).isNull()
        onView(withId(R.id.igb_back)).perform(click())
        assertThat(viewModelArchive?.backLiveData?.value?.peekContent()).isNotNull()
    }
}