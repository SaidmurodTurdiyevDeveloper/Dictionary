package com.example.dictionary.ui.screens.mixed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dictionary.R
import com.example.dictionary.contracts.mixed.ContractDictionaryItem
import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.data.model.DataMoveCountry
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.databinding.FragmentDictionaryItemBinding
import com.example.dictionary.ui.dialogs.DialogCountry
import com.example.dictionary.ui.viewModel.mixed.ViewModelDictionaryItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentDictionaryItem : Fragment(R.layout.fragment_dictionary_item),
    ContractDictionaryItem.View {
    private val viewModel: ViewModelDictionaryItem by viewModels()
    private var binding: FragmentDictionaryItemBinding? = null
    private var id: Long = -1

    //Observers
    private var _loadDataObservers = Observer<DictionaryEntity> {
        loadData(it)
    }
    private var _closeObserver = Observer<Unit> {
        closeWindow()
    }
    private var _loadCountryOneObserver = Observer<DataCountry> {
        loadCountryOne(it)
    }
    private var _loadCountryTwoObserver = Observer<DataCountry> {
        loadCountryTwo(it)
    }
    private var _loadLearnPrecentObserver = Observer<String> {
        loadDataLearnPracent(it)
    }
    private var _openInfoObserver = Observer<Event<String>> {
        val data = it.getContentIfNotHandled()
        if (data != null)
            openInfoText(data)
    }

    private var _openListObserver = Observer<Event<Unit>> {
        val data = it.getContentIfNotHandled()
        if (data != null)
            openList(id)
    }
    private var _openCountryOneDialogObserever = Observer<Event<DataMoveCountry>> {
        val data = it.getContentIfNotHandled()
        if (data != null) {
            openChangeLangugeDialogOne(data.countryId)
        }
    }
    private var _openCountryTwoDialogObserver = Observer<Event<DataMoveCountry>> {
        val data = it.getContentIfNotHandled()
        if (data != null) {
            val notdata = data.notCountry
            if (notdata != null)
                openChangeLangugeDialogTwo(notdata, data.countryId)
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
        binding?.countryOneFlag?.setOnClickListener { viewModel.openCountryDilog(true, id) }
        binding?.countryTwoFlag?.setOnClickListener { viewModel.openCountryDilog(false, id) }
        binding?.textInfo?.setOnClickListener { viewModel.openInfo(id) }
        binding?.buttonlist?.setOnClickListener { viewModel.openList() }
    }

    private fun registerObserver() {
        viewModel.loadLivedata.observe(viewLifecycleOwner, _loadDataObservers)
        viewModel.closeLiveData.observe(viewLifecycleOwner, _closeObserver)
        viewModel.loadCountryOneLivedata.observe(viewLifecycleOwner, _loadCountryOneObserver)
        viewModel.loadCountryTwoLivedata.observe(viewLifecycleOwner, _loadCountryTwoObserver)
        viewModel.loadLearnPrecentLivedata.observe(viewLifecycleOwner, _loadLearnPrecentObserver)
        viewModel.openCountryOneDialogLiveData.observe(
            viewLifecycleOwner,
            _openCountryOneDialogObserever
        )
        viewModel.openCountryTwoDialogLiveData.observe(
            viewLifecycleOwner,
            _openCountryTwoDialogObserver
        )
        viewModel.openInfoLiveData.observe(viewLifecycleOwner, _openInfoObserver)
        viewModel.openListLiveData.observe(viewLifecycleOwner, _openListObserver)
    }

    @SuppressLint("ResourceAsColor")
    override fun loadData(data: DictionaryEntity) {
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


    override fun loadCountryOne(dataCountry: DataCountry) {
        binding?.countryOneFlag?.setBackgroundResource(dataCountry.flag)
    }

    override fun loadCountryTwo(dataCountry: DataCountry) {
        binding?.countryTwoFlag?.setBackgroundResource(dataCountry.flag)
    }

    override fun loadDataLearnPracent(text: String) {
        binding?.percentListWordText?.text = text
    }

    override fun openChangeLangugeDialogOne(countryId: Int) {
        val d = DialogCountry(requireActivity(), countryId)
        d.submitListener {
            viewModel.setCountryOne(it, id)
        }
        d.show()
    }

    override fun openChangeLangugeDialogTwo(countryDismis: Int, countryId: Int) {
        val d = DialogCountry(requireActivity(), countryId, countryDismis)
        d.submitListener {
            viewModel.setCountryTwo(it, id)
        }
        d.show()
    }

    override fun openInfoText(textInfo: String) {
        val action =
            FragmentDictionaryItemDirections.actionFragmentDictionaryItemToFragmentDictionaryInfo()
        action.arguments.putString("text", textInfo)
        findNavController().navigate(action)
    }

    override fun openList(id: Long) {
        val action =
            FragmentDictionaryItemDirections.actionFragmentDictionaryItemToFragmentWordsList()
        findNavController().navigate(action)
    }

    override fun closeWindow() {
        findNavController().navigateUp()
    }

    private fun getUserId() {
        val bundle = requireArguments()
        id = bundle.getLong("id")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}