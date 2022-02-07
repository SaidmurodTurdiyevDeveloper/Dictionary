package com.example.dictionary.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dictionary.R
import com.example.dictionary.data.model.Event
import com.example.dictionary.ui.viewModel.impl.ViewModelSplash
import com.example.dictionary.utils.extention.loadOnlyOneTimeObserver
import com.example.dictionary.utils.extention.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSplash constructor(var defViewModel: ViewModelSplash? = null) : Fragment(R.layout.fragment_splash) {

    private lateinit var viewModel: ViewModelSplash

    private var openChooseLanguageobserver = Observer<Unit> {
        val action = FragmentSplashDirections.openChooseCauntry()
        findNavController().navigate(action)
    }
    private var openMainobserver = Observer<Unit> {
        val action = FragmentSplashDirections.openMain()
        findNavController().navigate(action)
    }
    private var loadingObserver = Observer<Event<Boolean>> {

    }
    private var showMesssage = Observer<Event<String>> {
        loadOnlyOneTimeObserver(it) {
            requireActivity().showToast(this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelSplash::class.java]
        viewModel.loadingScreenLiveData.observe(viewLifecycleOwner, loadingObserver)
        viewModel.openChooselanguageLiveData.observe(viewLifecycleOwner, openChooseLanguageobserver)
        viewModel.openMainScreenLiveData.observe(viewLifecycleOwner, openMainobserver)
        viewModel.showMessage.observe(viewLifecycleOwner, showMesssage)
        viewModel.init()
    }
}
