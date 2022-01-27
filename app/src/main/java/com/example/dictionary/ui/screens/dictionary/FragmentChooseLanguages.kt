package com.example.dictionary.ui.screens.dictionary

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dictionary.R
import com.example.dictionary.data.model.Event
import com.example.dictionary.databinding.FragmentSelectCountryBinding
import com.example.dictionary.ui.adapter.AdapterDropDown
import com.example.dictionary.ui.viewModel.dictionary.impl.ViewModelChooseCountry
import com.example.dictionary.utils.other.MyCountries
import com.example.dictionary.utils.extention.loadOnlyOneTimeObserver
import com.example.dictionary.utils.extention.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentChooseLanguages :
    Fragment(R.layout.fragment_select_country) {
    /*
    * Null objects
    * */
    private var binding: FragmentSelectCountryBinding? = null

    /*
    * ViewModel is created
    * */
    private val viewModel: ViewModelChooseCountry by viewModels()

    /**
     * Adapter
     * */
    /*
    * Observers is created
    * */
    private val _setCountryOneObserver = Observer<Event<Int>> {
        loadOnlyOneTimeObserver(it) {
            binding?.spCountryOne?.setSelection(this)
        }
    }

    private val _setCountryTwoObserver = Observer<Event<Int>> {
        loadOnlyOneTimeObserver(it) {
            binding?.spCountryTwo?.setSelection(this)
        }
    }

    private val _openNextObserver = Observer<Event<Unit>> {
        val data = it.getContentIfNotHandled()
        if (data != null)
            findNavController().navigate(FragmentChooseLanguagesDirections.actionOpenfragmentMainInChooselangiages())
    }
    private val _closeObserver = Observer<Event<Unit>> {
        val data = it.getContentIfNotHandled()
        if (data != null)
            findNavController().navigateUp()
    }

    /*
    * Override methods
    * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSelectCountryBinding.bind(view)
        registerObserver()
        loading()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    /*
    * Private Methods
    * */
    private fun loading() {
        binding?.spCountryOne?.apply {
            val country = MyCountries()
            adapter = AdapterDropDown(requireContext(), country.getCountries())
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    viewModel.clickFirstCountry(position)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    requireActivity().showToast("Nothing")
                }

            }
        }
        binding?.spCountryTwo?.apply {
            val country = MyCountries()
            adapter = AdapterDropDown(requireContext(), country.getCountries())
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    viewModel.clickSecondCountry(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    requireActivity().showToast("Nothing")
                }

            }
        }
        binding?.done?.setOnClickListener { viewModel.done() }
    }

    private fun registerObserver() {
        val owner: LifecycleOwner = this
        viewModel.loadCountryOneLiveData.observe(owner, _setCountryOneObserver)

        viewModel.loadCountryTwoLiveData.observe(owner, _setCountryTwoObserver)

        viewModel.openNextLiveData.observe(owner, _openNextObserver)

        viewModel.closeLiveData.observe(owner, _closeObserver)
    }
}