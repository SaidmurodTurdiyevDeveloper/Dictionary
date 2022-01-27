package com.example.dictionary.ui.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.example.dictionary.R
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.databinding.DialogDictionaryBinding
import com.example.dictionary.utils.other.sendOneParametreBlock

class DialogDictionary(context: Context, name: String = "Add") {
    @SuppressLint("InflateParams")
    private var view = LayoutInflater.from(context).inflate(R.layout.dialog_dictionary, null, false)
    private var listener: sendOneParametreBlock<DictionaryEntity>? = null
    private val dialog: AlertDialog = AlertDialog.Builder(context).setView(view).create()
    private var binding: DialogDictionaryBinding? = DialogDictionaryBinding.bind(view)
    private var data: DictionaryEntity = DictionaryEntity(0, "Dictioanary", "Info", 0, 0, 0, 0)

    fun submit(block: sendOneParametreBlock<DictionaryEntity>): DialogDictionary {
        listener = block
        return this
    }

    init {
        dialog.setOnDismissListener { binding = null }
        binding?.let { bd ->
            bd.btnOk.setOnClickListener { done() }
            bd.etInfo.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        done()
                        return true
                    }
                    return false
                }

            })

            bd.igbBack.setOnClickListener { dialog.dismiss() }
            bd.tvTitle.text = name
            bd.etName.setOnFocusChangeListener { _, _ -> bd.defEtNameLayout.isErrorEnabled = false }
        }
    }

    @SuppressLint("SetTextI18n")
    fun show(data: DictionaryEntity? = null) {
        if (data != null) binding?.let { bd ->
            bd.etName.setText(data.name)
            bd.etInfo.setText(data.dataInfo)
            this.data.apply {
                id = data.id
                learnPracent = data.learnPracent
                languageIdOne = data.languageIdOne
                languageIdTwo = data.languageIdTwo
                isDelete = data.isDelete
            }
            bd.tvTitle.text = "Update"
        }
        dialog.show()
    }

    private fun done() {
        binding?.let {bd->
            val text = bd.etName.text.toString()
            if (text.isNotBlank()) {
                data.name = text
                listener?.invoke(data)
                dialog.dismiss()
            } else {
                bd.defEtNameLayout.error = "Please,Enter dictionary name"
                bd.defEtNameLayout.isErrorEnabled = true
            }
        }
    }
}