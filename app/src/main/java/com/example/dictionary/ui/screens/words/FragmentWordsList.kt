package com.example.dictionary.ui.screens.words

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentWordsListBinding
import com.example.dictionary.ui.adapter.AdapterWord
import com.example.dictionary.ui.dialogs.DialogText
import com.example.dictionary.ui.menu.MenuWordsList
import com.example.dictionary.ui.viewModel.impl.word.ViewModelWordListImplament
import com.example.dictionary.utils.extention.loadOnlyOneTimeObserver
import com.example.dictionary.utils.extention.showToast
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentWordsList constructor(var defViewModel: ViewModelWordListImplament? = null) : Fragment(R.layout.fragment_words_list) {
    private lateinit var viewModel: ViewModelWordListImplament
    private val binding: FragmentWordsListBinding by viewBinding()
    private var dictionaryId = -1L

    @Inject
    lateinit var adapter: AdapterWord

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelWordListImplament::class.java]
        getDictionaryId()
        loadListener()
        loadObserver()
        viewModel.loadList(dictionaryId)
    }

    private fun loadListener() {
        binding.btnMenu.setOnClickListener {
            MenuWordsList(requireContext(), it).setFirstToSecondListener {
                viewModel.firstToSecond(dictionaryId)
            }.setSecondToFirstListener {
                viewModel.secondtoFirst(dictionaryId)
            }.setOpenAllListener {
                viewModel.openAll(dictionaryId)
            }.show()
        }
        binding.defActionBar.igbBack.setOnClickListener {
            viewModel.close()
        }
        adapter.submitlistenerFirst {
            if (it.isActiveFirst)
                viewModel.openItem(it)
            else
                viewModel.clickItem(it)
        }
        adapter.submitlistenerSecond {
            if (it.isActiveSecond)
                viewModel.openItem(it)
            else
                viewModel.clickItem(it)
        }
        binding.defRecyclerView.spRefresh.setOnRefreshListener {
            viewModel.loadList(dictionaryId)
        }
        binding.defRecyclerView.list.layoutManager = LinearLayoutManager(requireContext())
        binding.defRecyclerView.list.adapter = adapter
    }

    private fun loadObserver() {
        viewModel.toastLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                binding.defRecyclerView.spRefresh.isRefreshing = false
                requireActivity().showToast(this)
            }
        }

        viewModel.messageLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                binding.defRecyclerView.spRefresh.isRefreshing = false
                DialogText(requireContext(), "Message").show(this)
            }
        }

        viewModel.snackBarLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                Snackbar.make(requireContext(), binding.defWordListLayout, this, Snackbar.LENGTH_LONG).setAction(event.text) {
                    event.block?.invoke("Reload")
                }.show()
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                binding.defRecyclerView.spRefresh.isRefreshing = false
                binding.defErrorLayout.errorLayout.isVisible = true
                binding.defErrorLayout.tvError.text = this
            }
        }

        viewModel.listOfWordsLiveData.observe(viewLifecycleOwner) {
            binding.defRecyclerView.spRefresh.isRefreshing = false
            binding.defErrorLayout.errorLayout.isVisible = false
            adapter.differ.submitList(it)
        }

        viewModel.openItemInfoFlow.observe(viewLifecycleOwner) {
            loadOnlyOneTimeObserver(it) {
                val action = FragmentWordsListDirections.openWordsItemTwo(this)
                findNavController().navigate(action)
            }
        }

        viewModel.closeWindowLiveData.observe(viewLifecycleOwner) {
            loadOnlyOneTimeObserver(it) {
                findNavController().navigateUp()
            }
        }
    }

    private fun getDictionaryId() {
        val bundle = requireArguments()
        dictionaryId = bundle.getLong("Id")
    }
}