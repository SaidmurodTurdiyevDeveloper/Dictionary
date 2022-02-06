package com.example.dictionary.ui.screens.dictionary

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.databinding.FragmentDictionaryItemBinding
import com.example.dictionary.ui.dialogs.DialogText
import com.example.dictionary.ui.viewModel.impl.dictionary.ViewModelDictionaryItem
import com.example.dictionary.utils.extention.loadOnlyOneTimeObserver
import com.example.dictionary.utils.extention.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentDictionaryItem constructor(var defViewModel: ViewModelDictionaryItem? = null) : Fragment(R.layout.fragment_dictionary_item) {

    private lateinit var viewModel: ViewModelDictionaryItem
    private val binding: FragmentDictionaryItemBinding by viewBinding()
    private var id: Long = -1
    private var anim: AnimationDrawable? = null

    //Observers
    @SuppressLint("ResourceAsColor")
    private var loadDataObservers = Observer<DictionaryEntity> { data ->
        binding.defActionBar.tvTitle.text = data.name
        binding.textInfo.text = data.dataInfo
        when {
            data.learnPracent < 50 -> {
                binding.percentListWordView.setBackgroundColor(R.color.degreeOne)
            }
            data.learnPracent != 100 -> {
                binding.percentListWordView.setBackgroundColor(R.color.degreeTwo)
            }
            else -> {
                binding.percentListWordView.setBackgroundColor(R.color.degreeThree)
            }
        }
    }

    private var closeObserver = Observer<Unit> {
        findNavController().navigateUp()
    }

    private var loadCountryOneObserver = Observer<DataCountry> {
        binding.countryOneFlag.setBackgroundResource(it.flag)
    }

    private var loadCountryTwoObserver = Observer<DataCountry> {
        binding.countryTwoFlag.setBackgroundResource(it.flag)
    }

    private var loadLearnPrecentObserver = Observer<String> { text ->
        binding.percentListWordText.text = text
    }

    private val showMessageObserver = Observer<Event<String>> { event ->
        loadOnlyOneTimeObserver(event) {
            DialogText(requireContext(), "Message").submit {
            }.show(this)
        }
    }

    private val showToastObserver = Observer<Event<String>> { event ->
        loadOnlyOneTimeObserver(event) {
            requireContext().showToast(this)
        }
    }

    private var openListObserver = Observer<Event<Unit>> { event ->
        loadOnlyOneTimeObserver(event) {
            val action = FragmentDictionaryItemDirections.openWordsListTwo(id)
            findNavController().navigate(action)
        }
    }

    private var openInfoObserver = Observer<Event<Long>> { event ->
        loadOnlyOneTimeObserver(event) {
            val action = FragmentDictionaryItemDirections.openDictionaryInfo(this)
            findNavController().navigate(action)
        }
    }

    private val loadingScreenObserever = Observer<Event<Boolean>> { event ->
        loadOnlyOneTimeObserver(event) {
            binding.defLoading.loadingLayout.isVisible = this
            if (this) {
                binding.defLoading.progress.show()
            } else {
                binding.defLoading.progress.hide()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelDictionaryItem::class.java]
        anim = binding.buttonlist.background as AnimationDrawable?
        startAnim()
        getUserId()
        registerObserver()
        setViewModelActions()
    }

    private fun setViewModelActions() {
        binding.defActionBar.igbBack.setOnClickListener { viewModel.close() }
        binding.buttonlist.setOnClickListener { viewModel.openList() }
        binding.textInfo.setOnClickListener { viewModel.openInfo(id) }
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
        viewModel.openInfoLiveData.observe(viewLifecycleOwner, openInfoObserver)
    }

    private fun getUserId() {
        val bundle = requireArguments()
        id = bundle.getLong("Id")
    }

    override fun onPause() {
        super.onPause()
        anim?.let {
            if (it.isRunning) {
                it.stop()
            }
        }
    }

    private fun startAnim() {
        anim?.let {
            it.setEnterFadeDuration(2500)
            it.setExitFadeDuration(2500)
            if (!it.isRunning) {
                it.start()
            }
        }
    }
}