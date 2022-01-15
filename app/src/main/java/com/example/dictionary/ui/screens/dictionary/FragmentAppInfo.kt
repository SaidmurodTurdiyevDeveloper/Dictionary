package com.example.dictionary.ui.screens.dictionary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentAppInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAppInfo:Fragment(R.layout.fragment_app_info) {
    private val binding:FragmentAppInfoBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.igbBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}