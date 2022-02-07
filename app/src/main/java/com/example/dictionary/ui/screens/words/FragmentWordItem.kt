package com.example.dictionary.ui.screens.words

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentWordItemBinding
import com.example.dictionary.ui.dialogs.DialogTwoitemChoose
import com.example.dictionary.ui.viewModel.impl.word.ViewModelWordItemImplament
import com.example.dictionary.ui.viewModel.viewmodel_word.ViewModelWordItem
import com.example.dictionary.utils.extention.showToast
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FragmentWordItem constructor(var defViewModel: ViewModelWordItem? = null) : Fragment(R.layout.fragment_word_item) {
    private lateinit var viewModel: ViewModelWordItem
    private var wordId: Long = -1
    private var isChangedText = false
    private val binding: FragmentWordItemBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelWordItemImplament::class.java]
        getWordId()
        loadingListener()
        loadObserver()
        viewModel.loadData(wordId)
    }

    private fun loadingListener() {
        binding.fbDone.setOnClickListener {
            val text = binding.etExample.text.toString()
            viewModel.done(text)
        }
        binding.defActionBar.igbBack.setOnClickListener {
            viewModel.close(binding.etExample.text.toString())
        }
        binding.etExample.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.fbDone.hide()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.examplesTextChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                binding.fbDone.show()
            }
        })
        binding.etExample.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    val text = binding.etExample.text.toString()
                    viewModel.done(text)
                    true
                }
                else -> false
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isChangedText) {
                    val text = binding.etExample.text.toString()
                    viewModel.close(text)
                    remove()
                } else
                    findNavController().navigateUp()
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun loadObserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.closeWindowFlow.collectLatest {
                findNavController().navigateUp()
            }

            viewModel.exaplesTextChanged.collectLatest {
                isChangedText = it
            }

            viewModel.errorFlow.collectLatest {
                binding.fbDone.hide()
                binding.defActionBar.tvTitle.text = "Wrong"
                binding.etExample.setText("Empty")
                binding.tvFirstWord.text = "None"
                binding.tvSecondWord.text = "None"
            }

            viewModel.wordFlow.collectLatest {
                binding.fbDone.show()
                binding.defActionBar.tvTitle.text = "Word"
                binding.etExample.setText(it.example)
                binding.tvFirstWord.text = it.wordOne
                binding.tvSecondWord.text = it.wordTwo
            }

            viewModel.loadingFlow.collectLatest {
                binding.defLoading.loadingLayout.isVisible = it
                if (it) {
                    binding.defLoading.progress.show()
                    binding.defLoading.progress.animate()
                } else {
                    binding.defLoading.progress.hide()
                }
            }
            viewModel.showToastFlow.collectLatest {
                requireActivity().showToast(it)
            }
            viewModel.snackBarFlow.collectLatest { data ->
                Snackbar.make(binding.defWordItemLayout, data.peekContent(), Snackbar.LENGTH_LONG).setAction("Retry") {
                    data.block?.invoke("Retry")
                }.show()
            }
            viewModel.saveOrClose.collectLatest {
                DialogTwoitemChoose(requireContext(), "Message").submitRightChoose(
                    {
                        requireActivity().onBackPressed()
                    }, "Close"
                ).submitLeftChoose({
                    it.block?.invoke("Save")
                }, "Save").show(it.peekContent())
            }
        }
    }

    private fun getWordId() {
        val bundle = requireArguments()
        wordId = bundle.getLong("Id")
    }
}