package com.example.dictionary.ui.dialogs

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentRecyclerviewBinding
import com.example.dictionary.ui.adapter.AdapterCountry
import com.example.dictionary.utils.MyCountries

class DialogCountry(
    private var context: Context,
    private val countryId: Int,
    isNotCountry: Int? = null
) {
    @SuppressLint("InflateParams")
    private var view =
        LayoutInflater.from(context).inflate(R.layout.fragment_recyclerview, null, false)
    private var dialog: AlertDialog =
        AlertDialog.Builder(context).setView(view).create()

    private var mylist: MyCountries = MyCountries()


    private var listenerChoose: ((Int) -> Unit)? = null
    private var binding: FragmentRecyclerviewBinding? = FragmentRecyclerviewBinding.bind(view)

    private var adapter: AdapterCountry

    init {
        dialog.setOnDismissListener {
            binding = null
        }
        adapter = AdapterCountry(context, mylist.getCountries(), isNotCountry)
        binding?.let { binding1 ->
            binding1.imgActionBarFlag.setImageResource(mylist.getCountries()[countryId].flag)
            adapter.submitlistener {
                listenerChoose?.invoke(it)
                dialog.dismiss()
            }
            binding1.list.layoutManager = LinearLayoutManager(context)
            binding1.list.adapter = adapter

            binding1.tvTitle.text = "Countries"
        }
    }

    fun submitListener(block: ((Int) -> Unit)) {
        listenerChoose = block
    }

    fun show() {
        dialog.show()
    }
}



