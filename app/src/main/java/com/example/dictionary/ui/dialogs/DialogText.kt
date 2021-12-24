package com.example.dictionary.ui.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.dictionary.R
import com.example.dictionary.databinding.DialogTextBinding

class DialogText(context: Context, name: String) {
    @SuppressLint("InflateParams")
    private var view =
        LayoutInflater.from(context).inflate(R.layout.dialog_text, null, false)
    private var listener: (() -> Unit)? = null
    private val dialog: AlertDialog =
        AlertDialog.Builder(context).setView(view).create()
    private var binding: DialogTextBinding? = DialogTextBinding.bind(view)

    fun submit(f: (() -> Unit)) {
        listener = f
    }

    init {
        dialog.setOnDismissListener {
            binding = null
        }
        binding?.let {
            it.btnOk.setOnClickListener {
                listener?.invoke()
                dialog.dismiss()
            }
            it.igbBack.setOnClickListener { dialog.dismiss() }
            it.tvTitle.text = name
        }
    }

    fun show(text: String) {
        binding?.tvMassage?.text = text
        dialog.show()
    }
}