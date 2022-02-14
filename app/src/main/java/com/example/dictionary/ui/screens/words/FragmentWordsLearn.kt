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
import com.example.dictionary.databinding.FragmentWordsLearnBinding
import com.example.dictionary.ui.adapter.AdapterWordsDefault
import com.example.dictionary.ui.dialogs.DialogText
import com.example.dictionary.ui.dialogs.DialogWord
import com.example.dictionary.ui.menu.MenuItemWord
import com.example.dictionary.ui.viewModel.impl.word.ViewModelWordsLearnImplament
import com.example.dictionary.utils.extention.loadOnlyOneTimeObserver
import com.example.dictionary.utils.extention.showToast
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentWordsLearn constructor(var defViewModel: ViewModelWordsLearnImplament? = null) : Fragment(R.layout.fragment_words_learn) {
    private lateinit var viewModel: ViewModelWordsLearnImplament
    private val binding: FragmentWordsLearnBinding by viewBinding()
    private var dictionaryId = -1L

    @Inject
    lateinit var adapter: AdapterWordsDefault
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelWordsLearnImplament::class.java]
        getDictionaryId()
        loadListener()
        loadObserver()
        viewModel.loadList(dictionaryId)
    }

    private fun loadListener() {
        binding.defActionBar.igbBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.fbAdd.setOnClickListener {
            viewModel.add()
        }
        binding.btnList.setOnClickListener {
            val action = FragmentWordsLearnDirections.openWordsList(dictionaryId)
            findNavController().navigate(action)
        }
        binding.btnPlay.setOnClickListener {
            val action = FragmentWordsLearnDirections.openWordsPlay(dictionaryId)
            findNavController().navigate(action)
        }
        adapter.submitlistener {
            viewModel.clickItem(it)
        }
        adapter.submitLongPressListener { view, dataWord, _ ->
            MenuItemWord(
                requireActivity(),
                view
            ).setlistenerDelete {
                viewModel.delete(dataWord)
            }.setlistenerUpdate {
                viewModel.update(dataWord)
            }.show()
        }
        binding.defRecyclerView.spRefresh.setOnRefreshListener {
            viewModel.loadList(dictionaryId)
        }
        binding.defRecyclerView.list.layoutManager = LinearLayoutManager(requireContext())
        binding.defRecyclerView.list.adapter = adapter
    }

    private fun loadObserver() {
        viewModel.listOfWordsLiveData.observe(viewLifecycleOwner) {
            binding.defRecyclerView.spRefresh.isRefreshing = false
            binding.defErrorLayout.errorLayout.isVisible = false
            adapter.differ.submitList(it)
        }

        viewModel.openItemLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                val action = FragmentWordsLearnDirections.openWordsItem(this)
                findNavController().navigate(action)
            }
        }

        viewModel.closeWindowLiveData.observe(viewLifecycleOwner) {
            loadOnlyOneTimeObserver(it) {
                findNavController().navigateUp()
            }
        }

        viewModel.addDataLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                DialogWord(requireContext()).submitListener {
                    event.block?.invoke(it)
                }.show()
            }
        }

        viewModel.updateDataLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                DialogWord(requireContext()).submitListener {
                    event.block?.invoke(it)
                }.show(event.peekContent())
            }
        }

        viewModel.deletDataLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                DialogText(requireContext(), "Delete").setListener("delete") {
                    event.block?.invoke(this)
                }.show("This ${this.wordFirst} can be delete")
            }
        }

        viewModel.loadingLayoutLiveData.observe(viewLifecycleOwner) {
            binding.defLoading.loadingLayout.isVisible = it
            binding.defRecyclerView.spRefresh.isRefreshing = false
            if (it) {
                binding.defLoading.progress.show()
                binding.defLoading.progress.animate()
            } else binding.defLoading.progress.hide()
        }

        viewModel.toastLiveData.observe(viewLifecycleOwner) {
            loadOnlyOneTimeObserver(it) {
                binding.defRecyclerView.spRefresh.isRefreshing = false
                requireActivity().showToast(this)
            }
        }

        viewModel.messageLiveData.observe(viewLifecycleOwner) {
            loadOnlyOneTimeObserver(it) {
                binding.defRecyclerView.spRefresh.isRefreshing = false
                DialogText(requireContext(), "Message").show(this)
            }
        }

        viewModel.snackBarLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                binding.defRecyclerView.spRefresh.isRefreshing = false
                Snackbar.make(requireContext(), binding.defWordLearnLayout, this, Snackbar.LENGTH_LONG).setAction(event.text) {
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

    }

    private fun getDictionaryId() {
        val bundle = requireArguments()
        dictionaryId = bundle.getLong("Id")
    }
}