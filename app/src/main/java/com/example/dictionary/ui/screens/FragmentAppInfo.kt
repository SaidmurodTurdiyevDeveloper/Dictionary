package com.example.dictionary.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentAppInfoBinding
import com.example.dictionary.ui.viewModel.ViewModelAppinfo
import com.example.dictionary.ui.viewModel.impl.ViewModelAppInfoImplament
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentAppInfo  constructor(var defViewModel: ViewModelAppinfo? = null) : Fragment(R.layout.fragment_app_info) {
    private lateinit var viewModel: ViewModelAppinfo
    private val binding: FragmentAppInfoBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelAppInfoImplament::class.java]
        binding.defActionBar.igbBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}