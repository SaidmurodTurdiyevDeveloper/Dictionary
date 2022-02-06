package com.example.dictionary.ui.screens.words

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentWordsLearnBinding
import com.example.dictionary.ui.viewModel.impl.word.ViewModelWordsLearnImplament
import com.example.dictionary.ui.viewModel.viewmodel_word.ViewModelWordsLearn
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentWordsLearn  constructor(var defViewModel: ViewModelWordsLearn? = null) : Fragment(R.layout.fragment_words_learn) {
    private lateinit var viewModel: ViewModelWordsLearn
    private val binding: FragmentWordsLearnBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelWordsLearnImplament::class.java]
    }
}