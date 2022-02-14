package com.example.dictionary.ui.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.dictionary.R
import com.example.dictionary.data.model.DataWord
import com.example.dictionary.databinding.DialogWordBinding
import com.example.dictionary.utils.other.sendOneParametreBlock

class DialogWord(context: Context, var name: String = "Add") {
    @SuppressLint("InflateParams")
    private var view = LayoutInflater.from(context).inflate(R.layout.dialog_word, null, false)
    private var listener: sendOneParametreBlock<DataWord>? = null
    private val dialog: AlertDialog = AlertDialog.Builder(context).setView(view).create()
    private var binding: DialogWordBinding? = DialogWordBinding.bind(view)
    private var data: DataWord = DataWord(-1, "", "", "", 0, 0, false, false)

    fun submitListener(block: sendOneParametreBlock<DataWord>): DialogWord {
        listener = block
        return this
    }

    init {
        dialog.setOnDismissListener { binding = null }
        binding?.let { bd ->
            bd.btnOk.setOnClickListener {
                done()
            }
            bd.defActionBar.igbBack.setOnClickListener { dialog.dismiss() }
            bd.defActionBar.tvTitle.text = name
            bd.etFirstWord.setOnFocusChangeListener { _, _ -> bd.defEtFirstWordLayout.isErrorEnabled = false }
            bd.etSecondWord.setOnFocusChangeListener { _, _ -> bd.defEtSecondWordLayout.isErrorEnabled = false }
        }
    }

    @SuppressLint("SetTextI18n")
    fun show(data: DataWord? = null) {
        if (data != null) binding?.let { bd ->
            bd.etFirstWord.setText(data.wordFirst)
            bd.etSecondWord.setText(data.wordSecond)
            this.data.apply {
                id = data.id
                learnCount = data.learnCount
                example = data.example
                dictionaryId = data.dictionaryId
            }
            bd.defActionBar.tvTitle.text = "Update"
            bd.btnOk.text = "update"
        }
        dialog.show()
    }

    private fun done() {
        binding?.let { bd ->
            val text = bd.etFirstWord.text.toString().trim()
            val textwordTwo = bd.etSecondWord.text.toString().trim()
            if (!text.equals("")) {
                if (!textwordTwo.equals("")) {
                    data.wordFirst = text
                    data.wordSecond = textwordTwo
                    listener?.invoke(data)
                    dialog.dismiss()
                } else {
                    bd.defEtSecondWordLayout.error = "Please,Enter translate"
                    bd.defEtSecondWordLayout.isErrorEnabled = true
                }
            } else {
                bd.defEtFirstWordLayout.error = "Please,Enter word"
                bd.defEtFirstWordLayout.isErrorEnabled = true
            }
        }
    }
}