package com.example.dictionary.ui.screens.dictionary

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentDictionaryInfoBinding
import com.example.dictionary.ui.dialogs.DialogText
import com.example.dictionary.ui.dialogs.DialogTwoitemChoose
import com.example.dictionary.ui.viewModel.impl.dictionary.ViewModelDictionaryInfoImplament
import com.example.dictionary.ui.viewModel.viewmodel_dictionary.ViewModelDictionaryInfo
import com.example.dictionary.utils.extention.showToast
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FragmentDictionaryInfo constructor(var defViewModel: ViewModelDictionaryInfo? = null) : Fragment(R.layout.fragment_dictionary_info) {

    private lateinit var viewModel: ViewModelDictionaryInfo
    private val binding: FragmentDictionaryInfoBinding by viewBinding()
    private var id: Long = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelDictionaryInfoImplament::class.java]
        getDictionaryId()
        loadingListener()
        loadViewModelobserver()
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

    private fun loadViewModelobserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.closeWindowFlow.collectLatest {
                findNavController().navigateUp()
            }
            viewModel.messageFlow.collectLatest {
                DialogText(requireContext(), "Message").setListener("Close") {

                }.show(it)
            }
            viewModel.dictionaryFlow.collectLatest {
                binding.defActionBar.tvTitle.text = it.name
                binding.etInfo.setText(it.dataInfo)
            }
            viewModel.saveOrDoesNotSaveDataFlow.collectLatest {
                DialogTwoitemChoose(requireContext(), it).submitLeftChoose({
                    viewModel.done(binding.etInfo.text.toString())
                }, "Save").submitRightChoose({
                    findNavController().navigateUp()
                }, "Close").show("Do you want to save?")
            }
            viewModel.showToastFlow.collectLatest {
                requireActivity().showToast(it)
            }
            viewModel.errorFlow.collectLatest {
                Snackbar.make(binding.defDictionaryInfoLayout, it, Snackbar.LENGTH_LONG).setAction("Reload") {
                    getId()
                    viewModel.loadData(id)
                }.show()
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
        }
    }
}



