package com.example.dictionary.ui.screens.dictionary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentDictionaryInfoBinding
import com.example.dictionary.ui.viewModel.impl.dictionary.ViewModelDictionaryInfoImplament
import com.example.dictionary.ui.viewModel.viewmodel_dictionary.ViewModelDictionaryInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentDictionaryInfo constructor(var defViewModel: ViewModelDictionaryInfo? = null) : Fragment(R.layout.fragment_dictionary_info) {

    private lateinit var viewModel: ViewModelDictionaryInfo
    private val binding: FragmentDictionaryInfoBinding by viewBinding()
    private var id: Long = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelDictionaryInfoImplament::class.java]
        getDictionaryId()
        loadingListener()
    }

    private fun getDictionaryId() {
        val bundle = requireArguments()
        id = bundle.getLong("Id", -1)
    }

    private fun loadingListener() {
        binding.fbDone.setOnClickListener {
            val text = binding.etInfo.text.toString()
            viewModel.done(text)
        }
        binding.defActionBar.igbBack.setOnClickListener {
            val text = binding.etInfo.text.toString()
            viewModel.close(text)
        }
    }
}



