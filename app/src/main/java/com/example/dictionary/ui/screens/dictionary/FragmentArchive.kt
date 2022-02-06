package com.example.dictionary.ui.screens.dictionary

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.R
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.databinding.FragmentArchiveBinding
import com.example.dictionary.ui.adapter.AdapterDictionary
import com.example.dictionary.ui.dialogs.DialogText
import com.example.dictionary.ui.dialogs.DialogTwoitemChoose
import com.example.dictionary.ui.menu.MenuArchive
import com.example.dictionary.ui.viewModel.impl.dictionary.ViewModelArchive
import com.example.dictionary.utils.extention.loadOnlyOneTimeObserver
import com.example.dictionary.utils.extention.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentArchive constructor(var testDefViewModel: ViewModelArchive? = null) : Fragment(R.layout.fragment_archive) {
    /*
    * Private null varable
    * */
    private var _binding: FragmentArchiveBinding? = null
    private val binding: FragmentArchiveBinding get() = _binding!!

    /*
    * ViewModel
    * */
    private lateinit var viewmodel: ViewModelArchive

    @Inject
    lateinit var adapter: AdapterDictionary

    /*
    * Observers
    * */
    private var loadingObserver = Observer<Event<List<DictionaryEntity>>> {
        loadOnlyOneTimeObserver(it) {
            if (binding.defLoadingLayout.loadingLayout.isVisible)
                binding.defLoadingLayout.loadingLayout.isVisible = false
            if (binding.defErrorLayout.errorLayout.isVisible)
                binding.defErrorLayout.errorLayout.isVisible = false
            adapter.submitList(this)
        }
    }

    private var itemTouchObserver = Observer<Event<DictionaryEntity>> {
        loadOnlyOneTimeObserver(it) {
            val dialog = DialogTwoitemChoose(requireContext(), "Item")
            dialog.submitRightChoose({
                viewmodel.delete(this)
            })
            dialog.submitLeftChoose({
                viewmodel.returnToActive(this)
            })
            dialog.show("this element (${this.name}) is returned to active or is deleted in database")
        }
    }

    private var backObserver = Observer<Event<Unit>> {
        findNavController().navigateUp()
    }

    private var deleteAllObserver = Observer<Event<Unit>> {event->
        loadOnlyOneTimeObserver(event) {
            val dialog = DialogText(requireContext(), "Clear Archive")
            dialog.submit {
                event.block?.invoke(Unit)
            }
            dialog.show("All items are deleting")
        }
    }
    private var showMassageEmptyObserver = Observer<Event<String>> {
        loadOnlyOneTimeObserver(it) {
            if (!binding.defErrorLayout.errorLayout.isVisible)
                binding.defErrorLayout.errorLayout.isVisible = true
            binding.defErrorLayout.tvError.text = this
        }
    }
    private var showMessage = Observer<Event<String>> {
        loadOnlyOneTimeObserver(it) {
            requireActivity().showToast(this)
        }
    }

    private var changeLoadingProgres = Observer<Event<Boolean>> {
        loadOnlyOneTimeObserver(it) {
            binding.defLoadingLayout.loadingLayout.isVisible = this
            if (this) {
                binding.defLoadingLayout.progress.show()
            } else {
                binding.defLoadingLayout.progress.hide()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentArchiveBinding.bind(view)
        viewmodel = testDefViewModel ?: ViewModelProvider(requireActivity())[ViewModelArchive::class.java]
        loadingViews()
        registerObserver()
        viewmodel.getArchiveList()
    }

    private fun loadingViews() {
        /*
        * Adapter is created
        * */
        adapter.submitListenerItemTouch {
            viewmodel.itemTouch(it)
        }

        binding.defList.list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.defList.list.adapter = adapter

        binding.defActionBar.igbBack.setOnClickListener {
            viewmodel.back()
        }

        binding.btnMenu.setOnClickListener {
            MenuArchive(requireContext(), it).setClearListener {
                viewmodel.clearAll()
            }
                .show()
        }
    }

    private fun registerObserver() {
        viewmodel.backLiveData.observe(viewLifecycleOwner, backObserver)
        viewmodel.itemTouchLiveData.observe(viewLifecycleOwner, itemTouchObserver)
        viewmodel.loadAllDataLiveData.observe(viewLifecycleOwner, loadingObserver)
        viewmodel.openDialogDeleteAllLiveData.observe(viewLifecycleOwner, deleteAllObserver)
        viewmodel.showMessageEmptyLiveData.observe(viewLifecycleOwner, showMassageEmptyObserver)
        viewmodel.showMessageToastLiveData.observe(viewLifecycleOwner, showMessage)
        viewmodel.loadingLiveData.observe(viewLifecycleOwner, changeLoadingProgres)
    }
}