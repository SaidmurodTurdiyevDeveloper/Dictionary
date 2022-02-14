package com.example.dictionary.ui.screens.dictionary

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentDictionaryInfoBinding
import com.example.dictionary.ui.dialogs.DialogText
import com.example.dictionary.ui.dialogs.DialogTwoitemChoose
import com.example.dictionary.ui.viewModel.impl.dictionary.ViewModelDictionaryInfoImplament
import com.example.dictionary.utils.extention.loadOnlyOneTimeObserver
import com.example.dictionary.utils.extention.showToast
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentDictionaryInfo constructor(var defViewModel: ViewModelDictionaryInfoImplament? = null) : Fragment(R.layout.fragment_dictionary_info) {

    private lateinit var viewModel: ViewModelDictionaryInfoImplament
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
        viewModel.toastLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                requireActivity().showToast(this)
            }
        }

        viewModel.messageLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                DialogText(requireContext(), "Message").show(this)
            }
        }

        viewModel.snackBarLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                Snackbar.make(requireContext(), binding.defDictionaryInfoLayout, this, Snackbar.LENGTH_LONG).setAction(event.text) {
                    event.block?.invoke("Reload")
                }.show()
            }
        }

        viewModel.closeWindowLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                findNavController().navigateUp()
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { event ->
            binding.etInfo.setText(event.peekContent())
        }

        viewModel.loadingLayoutLiveData.observe(viewLifecycleOwner) {
            binding.defLoading.loadingLayout.isVisible = it
            if (it) {
                binding.defLoading.progress.show()
                binding.defLoading.progress.animate()
            } else {
                binding.defLoading.progress.hide()
            }
        }

        viewModel.dictionaryLiveData.observe(viewLifecycleOwner) {
            binding.defActionBar.tvTitle.text = it.name
            binding.etInfo.setText(it.dataInfo)
        }

        viewModel.saveOrDoesNotSaveDataLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                DialogTwoitemChoose(requireContext(), this).submitLeftChoose({
                    viewModel.done(binding.etInfo.text.toString())
                }, "Save").submitRightChoose({
                    requireActivity().onBackPressed()
                }, "Close").show("Do you want to save?")
            }
        }
    }
}



