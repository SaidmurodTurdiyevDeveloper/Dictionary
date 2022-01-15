package com.example.dictionary.ui.screens.dictionary

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.R
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.databinding.FragmentArxiveBinding
import com.example.dictionary.ui.adapter.AdapterDictionary
import com.example.dictionary.ui.dialogs.DialogText
import com.example.dictionary.ui.dialogs.DialogTwoitemChoose
import com.example.dictionary.ui.menu.MenuArchive
import com.example.dictionary.ui.viewModel.dictionary.ViewModelArxive
import com.example.dictionary.utils.extention.loadOnlyOneTimeObserver
import com.example.dictionary.utils.extention.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentArxiv : Fragment(R.layout.fragment_arxive) {
    /*
    * Private null varable
    * */
    private var _binding: FragmentArxiveBinding? = null
    private val binding: FragmentArxiveBinding get() = _binding!!
    private var adapter: AdapterDictionary? = null

    /*
    * ViewModel
    * */
    private val viewmodel: ViewModelArxive by viewModels()

    /*
    * Observers
    * */
    private var loadingObserver = Observer<Event<List<DictionaryEntity>>> {
        loadOnlyOneTimeObserver(it) {
            if (binding.loadingAndMessageLayout.isVisible)
                binding.loadingAndMessageLayout.visibility = ConstraintLayout.INVISIBLE
            adapter?.submitList(this)
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
            if (!binding.loadingAndMessageLayout.isVisible) {
                binding.loadingAndMessageLayout.visibility = ConstraintLayout.VISIBLE
            }
            binding.textEmptylist.text = this
        }
    }
    private var showMessage = Observer<Event<String>> {
        loadOnlyOneTimeObserver(it) {
            requireActivity().showToast(this)
        }
    }

    private var changeLoadingProgres = Observer<Event<Boolean>> {
        loadOnlyOneTimeObserver(it) {
            if (this) {
                if (!binding.loadingAndMessageLayout.isVisible) {
                    binding.loadingAndMessageLayout.visibility = ConstraintLayout.VISIBLE
                }
                binding.progresBar.show()
            } else {
                if (binding.loadingAndMessageLayout.isVisible) {
                    binding.progresBar.hide()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentArxiveBinding.bind(view)
        loadingViews()
        registerObserver()
    }

    private fun loadingViews() {
        /*
        * Adapter is created
        * */
        adapter = AdapterDictionary(requireContext())
        adapter?.submitListenerItemTouch {
            viewmodel.itemTouch(it)
        }

        binding.list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.list.adapter = adapter

        binding.igbBack.setOnClickListener {
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
        viewmodel.getArchiveList()
    }
}