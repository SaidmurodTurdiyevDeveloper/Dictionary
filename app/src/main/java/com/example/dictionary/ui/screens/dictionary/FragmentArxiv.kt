package com.example.dictionary.ui.screens.dictionary

import android.os.Bundle
import android.view.View
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
import com.example.dictionary.ui.viewModel.dictionary.ViewModelArxive
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentArxiv : Fragment(R.layout.fragment_arxive) {
    /*
    * Private null varable
    * */
    private var binding: FragmentArxiveBinding? = null
    private var adapter: AdapterDictionary? = null

    /*
    * ViewModel
    * */
    private val viewmodel: ViewModelArxive by viewModels()

    /*
    * Observers
    * */
    private var loadingObserver = Observer<Event<List<DictionaryEntity>>> {
        adapter?.submitList(it.peekContent())
    }

    private var itemTouchObserver = Observer<Event<DictionaryEntity>> {
        val data = it.getContentIfNotHandled()
        if (data != null) {
            val dialog = DialogTwoitemChoose(requireContext(), "Item")
            dialog.submitRightChoose({
                viewmodel.update(data)
            })
            dialog.submitLeftChoose({
                viewmodel.delete(data)
            })
            dialog.show("this element (${data.name}) is returned to active or is deleted in database")
        }

    }

    private var backObserver = Observer<Event<Unit>> {
        findNavController().navigateUp()
    }

    private var deleteAllObserver = Observer<Event<Int>> {
        val data = it.getContentIfNotHandled()
        if (data != null) {
            val dialog = DialogText(requireContext(), "Clear Archive")
            dialog.submit {
                viewmodel.deleteAll()
            }
            dialog.show("All items ($data) in the archive will be deleted")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentArxiveBinding.bind(view)
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
        binding?.list?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.list?.adapter = adapter
        binding?.igbBack?.setOnClickListener {
            viewmodel.back()
        }
        binding?.btnDelete?.setOnClickListener {
            viewmodel.deleteAll()
        }
    }

    private fun registerObserver() {
        viewmodel.backLiveData.observe(viewLifecycleOwner, backObserver)
        viewmodel.itemTouchLiveData.observe(viewLifecycleOwner, itemTouchObserver)
        viewmodel.loadingLiveData.observe(viewLifecycleOwner, loadingObserver)
        viewmodel.openDialogDeleteAllLiveData.observe(viewLifecycleOwner, deleteAllObserver)
    }
}