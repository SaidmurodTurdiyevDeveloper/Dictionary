package com.example.dictionary.ui.screens.words

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentWordsPlayBinding
import com.example.dictionary.ui.viewModel.impl.word.ViewModelWordsplayImplament
import com.example.dictionary.ui.viewModel.viewmodel_word.ViewModelWordsPlay
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentWordsPlay  constructor(var defViewModel: ViewModelWordsPlay? = null) : Fragment(R.layout.fragment_words_play) {
    private var anim: AnimationDrawable? = null
    private lateinit var viewModel: ViewModelWordsPlay
    private val binding: FragmentWordsPlayBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelWordsplayImplament::class.java]
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