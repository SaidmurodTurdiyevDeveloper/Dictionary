package com.example.dictionary.ui.screens.words

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dictionary.R
import com.example.dictionary.ui.viewModel.impl.word.ViewModelWordItemImplament
import com.example.dictionary.ui.viewModel.viewmodel_word.ViewModelWordItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentWordItem constructor(var defViewModel: ViewModelWordItem? = null) : Fragment(R.layout.fragment_word_item) {
    private lateinit var viewModel: ViewModelWordItem
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelWordItemImplament::class.java]
    }
}