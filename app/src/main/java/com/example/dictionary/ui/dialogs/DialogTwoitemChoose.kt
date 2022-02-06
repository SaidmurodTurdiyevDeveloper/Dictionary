package com.example.dictionary.ui.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.dictionary.R
import com.example.dictionary.databinding.DialogChooseBinding
import com.example.dictionary.utils.other.emptyBlock

class DialogTwoitemChoose(context: Context, textActionBar: String) {
    @SuppressLint("InflateParams")
    private var view = LayoutInflater.from(context).inflate(R.layout.dialog_choose, null, false)
    private var binding: DialogChooseBinding? = DialogChooseBinding.bind(view)
    private var _listenerRightChoose: emptyBlock? = null
    private var _listenerLeftChoose: emptyBlock? = null
    private var dialog = AlertDialog.Builder(context).setView(view).create()

    fun submitRightChoose(block: emptyBlock, textButton: String? = null): DialogTwoitemChoose {
        _listenerRightChoose = block
        if (textButton != null) binding?.btnRight?.text = textButton
        return this
    }

    fun submitLeftChoose(block: emptyBlock, textButton: String? = null): DialogTwoitemChoose {
        _listenerLeftChoose = block
        if (textButton != null) binding?.btnLeft?.text = textButton
        return this
    }

    init {
        dialog.setOnDismissListener { binding = null }
        binding?.let {
            it.defActionBar.tvTitle.text = textActionBar
            it.defActionBar.igbBack.setOnClickListener { dialog.dismiss() }
            it.btnRight.setOnClickListener {
                _listenerRightChoose?.invoke()
                dialog.dismiss()
            }
            it.btnLeft.setOnClickListener {
                _listenerLeftChoose?.invoke()
                dialog.dismiss()
            }
        }
    }

    fun show(textMessage: String) {
        binding?.tvMassage?.text = textMessage
        dialog.show()
    }
}