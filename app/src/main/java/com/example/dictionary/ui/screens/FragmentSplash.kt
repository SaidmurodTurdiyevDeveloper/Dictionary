package com.example.dictionary.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dictionary.R
import com.example.dictionary.data.source.local.shared.SharedDatabese
import com.example.dictionary.utils.other.MyStringObjects
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentSplash : Fragment(R.layout.fragment_splash) {

    @Inject
    lateinit var storadge: SharedDatabese

    private var _liveData = MutableLiveData<Unit>()
    private var _liveData2 = MutableLiveData<Unit>()

    private var _openChooseLanguageobserver = Observer<Unit> {
        val action = FragmentSplashDirections.actionFragmentSplashToFragmentChooseLanguages()
        findNavController().navigate(action)
    }
    private var _openMainobserver = Observer<Unit> {
        val action = FragmentSplashDirections.actionFragmentSplashToFragmentMain()
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _liveData.observe(viewLifecycleOwner, _openMainobserver)
        _liveData2.observe(viewLifecycleOwner, _openChooseLanguageobserver)
        lifecycleScope.launch {
            val id = storadge.getBoolenData(MyStringObjects.FIRST_ENTER_MODE)
            delay(1500)
            if (id) {
                _liveData.postValue(Unit)
            } else {
                _liveData2.postValue(Unit)
            }
        }
    }
}
