package com.example.dictionary.ui.screens.dictionary

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dictionary.R
import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.databinding.FragmentDictionaryItemBinding
import com.example.dictionary.ui.dialogs.DialogText
import com.example.dictionary.ui.viewModel.dictionary.impl.ViewModelDictionaryItem
import com.example.dictionary.utils.extention.loadOnlyOneTimeObserver
import com.example.dictionary.utils.extention.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentDictionaryItem : Fragment(R.layout.fragment_dictionary_item) {

    private val viewModel: ViewModelDictionaryItem by viewModels()
    private var binding: FragmentDictionaryItemBinding? = null
    private var id: Long = -1

    //Observers
    @SuppressLint("ResourceAsColor")
    private var loadDataObservers = Observer<DictionaryEntity> { data ->
        binding?.actionbarText?.text = data.name
        binding?.textInfo?.text = data.dataInfo
        when {
            data.learnPracent < 50 -> {
                binding?.percentListWordView?.setBackgroundColor(R.color.degreeOne)
            }
            data.learnPracent != 100 -> {
                binding?.percentListWordView?.setBackgroundColor(R.color.degreeTwo)
            }
            else -> {
                binding?.percentListWordView?.setBackgroundColor(R.color.degreeThree)
            }
        }
    }

    private var closeObserver = Observer<Unit> {
        findNavController().navigateUp()
    }

    private var loadCountryOneObserver = Observer<DataCountry> {
        binding?.countryOneFlag?.setBackgroundResource(it.flag)
    }

    private var loadCountryTwoObserver = Observer<DataCountry> {
        binding?.countryTwoFlag?.setBackgroundResource(it.flag)
    }

    private var loadLearnPrecentObserver = Observer<String> { text ->
        binding?.percentListWordText?.text = text
    }

    private val showMessageObserver = Observer<Event<String>> { event ->
        loadOnlyOneTimeObserver(event) {
            DialogText(requireContext(), "Message").show(this)
        }
    }

    private val showToastObserver = Observer<Event<String>> { event ->
        loadOnlyOneTimeObserver(event) {
            requireContext().showToast(this)
        }
    }

    private var openListObserver = Observer<Event<Unit>> { event ->
        loadOnlyOneTimeObserver(event) {
            val action = FragmentDictionaryItemDirections.actionFragmentDictionaryItemToFragmentWordsList(id)
            findNavController().navigate(action)
        }
    }

    private val loadingScreenObserever = Observer<Event<Boolean>> { event ->
        loadOnlyOneTimeObserver(event) {
            if (this) {
                if (binding?.layoutLoadingMain?.visibility == View.GONE) {
                    binding?.progress?.show()
                    binding?.layoutLoadingMain?.visibility = View.VISIBLE
                }
            } else {
                if (binding?.layoutLoadingMain?.visibility == View.VISIBLE) {
                    binding?.progress?.hide()
                    binding?.layoutLoadingMain?.visibility = View.GONE
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getUserId()
        binding = FragmentDictionaryItemBinding.bind(view)
        registerObserver()
        setViewModelActions()
    }

    private fun setViewModelActions() {
        binding?.igbBack?.setOnClickListener { viewModel.close() }
        binding?.buttonlist?.setOnClickListener { viewModel.openList() }
    }

    private fun registerObserver() {
        viewModel.loadLivedata.observe(viewLifecycleOwner, loadDataObservers)
        viewModel.closeLiveData.observe(viewLifecycleOwner, closeObserver)
        viewModel.loadCountryOneLivedata.observe(viewLifecycleOwner, loadCountryOneObserver)
        viewModel.loadCountryTwoLivedata.observe(viewLifecycleOwner, loadCountryTwoObserver)
        viewModel.loadLearnPrecentLivedata.observe(viewLifecycleOwner, loadLearnPrecentObserver)
        viewModel.openListLiveData.observe(viewLifecycleOwner, openListObserver)
        viewModel.showMessageLiveData.observe(viewLifecycleOwner, showMessageObserver)
        viewModel.showToastLiveData.observe(viewLifecycleOwner, showToastObserver)
        viewModel.loadingScreenLivedata.observe(viewLifecycleOwner, loadingScreenObserever)
    }

    private fun getUserId() {
        val bundle = requireArguments()
        id = bundle.getLong("Id")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}