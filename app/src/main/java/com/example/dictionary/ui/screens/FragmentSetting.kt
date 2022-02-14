package com.example.dictionary.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentSettingBinding
import com.example.dictionary.ui.viewModel.impl.ViewModelSettingImplament
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSetting constructor(var defViewModel: ViewModelSettingImplament? = null) : Fragment(R.layout.fragment_setting) {
    private lateinit var viewModel: ViewModelSettingImplament
    private val binding: FragmentSettingBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelSettingImplament::class.java]
    }
}