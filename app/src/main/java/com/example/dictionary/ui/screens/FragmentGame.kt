package com.example.dictionary.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentGameBinding
import com.example.dictionary.ui.viewModel.ViewModelGame
import com.example.dictionary.ui.viewModel.impl.ViewModelGameImplament
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentGame  constructor(var defViewModel: ViewModelGame? = null) : Fragment(R.layout.fragment_game) {
    private lateinit var viewModel: ViewModelGame
    private val binding: FragmentGameBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelGameImplament::class.java]
    }
}