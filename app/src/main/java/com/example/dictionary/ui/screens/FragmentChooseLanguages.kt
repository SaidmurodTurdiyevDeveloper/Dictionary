package com.example.dictionary.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.data.model.Event
import com.example.dictionary.databinding.FragmentSelectCountryBinding
import com.example.dictionary.ui.adapter.AdapterDropDown
import com.example.dictionary.ui.viewModel.impl.ViewModelChooseCountry
import com.example.dictionary.utils.extention.loadOnlyOneTimeObserver
import com.example.dictionary.utils.extention.showToast
import com.example.dictionary.utils.other.MyCountries
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentChooseLanguages  constructor(var defViewModel: ViewModelChooseCountry? = null) : Fragment(R.layout.fragment_select_country) {

    private val binding: FragmentSelectCountryBinding by viewBinding()
    private lateinit var viewModel: ViewModelChooseCountry

    /*
    * Observers is created
    * */
    private val _setCountryOneObserver = Observer<Event<Int>> {
        loadOnlyOneTimeObserver(it) {
            binding.spCountryOne.setSelection(this)
        }
    }

    private val _setCountryTwoObserver = Observer<Event<Int>> {
        loadOnlyOneTimeObserver(it) {
            binding.spCountryTwo.setSelection(this)
        }
    }

    private val _openNextObserver = Observer<Event<Unit>> {
        val data = it.getContentIfNotHandled()
        if (data != null)
            findNavController().navigate(FragmentChooseLanguagesDirections.openMainTwo())
    }
    private val _closeObserver = Observer<Event<Unit>> {
        val data = it.getContentIfNotHandled()
        if (data != null)
            findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelChooseCountry::class.java]
        registerObserver()
        loading()
    }

    /*
    * Private Methods
    * */
    private fun loading() {
        binding.spCountryOne.apply {
            val country = MyCountries()
            adapter = AdapterDropDown(requireContext(), country.getCountries())
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    viewModel.clickFirstCountry(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    requireActivity().showToast("Nothing")
                }

            }
        }
        binding.spCountryTwo.apply {
            val country = MyCountries()
            adapter = AdapterDropDown(requireContext(), country.getCountries())
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    viewModel.clickSecondCountry(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    requireActivity().showToast("Nothing")
                }

            }
        }
        binding.done.setOnClickListener { viewModel.done() }
    }

    private fun registerObserver() {
        val owner: LifecycleOwner = this
        viewModel.loadCountryOneLiveData.observe(owner, _setCountryOneObserver)

        viewModel.loadCountryTwoLiveData.observe(owner, _setCountryTwoObserver)

        viewModel.openNextLiveData.observe(owner, _openNextObserver)

        viewModel.closeLiveData.observe(owner, _closeObserver)
    }
}