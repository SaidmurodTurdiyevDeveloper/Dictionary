package com.example.dictionary.ui.screens.words

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentWordsListBinding
import com.example.dictionary.ui.viewModel.impl.word.ViewModelWordListImplament
import com.example.dictionary.ui.viewModel.viewmodel_word.ViewModelWordList
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentWordsList  constructor(var defViewModel: ViewModelWordList? = null) : Fragment(R.layout.fragment_words_list) {
    private lateinit var viewModel: ViewModelWordList
    private val binding: FragmentWordsListBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelWordListImplament::class.java]
    }
}