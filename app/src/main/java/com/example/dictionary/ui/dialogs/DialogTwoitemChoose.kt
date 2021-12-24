package com.example.dictionary.ui.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.dictionary.R
import com.example.dictionary.databinding.DialogChooceBinding

class DialogTwoitemChoose(
    context: Context,
    textActionBar: String
) {
    @SuppressLint("InflateParams")
    private var view =
        LayoutInflater.from(context).inflate(R.layout.dialog_chooce, null, false)
    private var binding: DialogChooceBinding? = DialogChooceBinding.bind(view)
    private var _listenerRightChoose: (() -> Unit)? = null
    private var _listenerLeftChoose: (() -> Unit)? = null
    private var dialog = AlertDialog.Builder(context).setView(view).create()

    fun submitRightChoose(f: () -> Unit, textButton: String? = null) {
        _listenerRightChoose = f
        if (textButton != null) {
            binding?.btnRight?.text = textButton
        }
    }

    fun submitLeftChoose(f: () -> Unit, textButton: String? = null) {
        _listenerLeftChoose = f
        if (textButton != null) {
            binding?.btnLeft?.text = textButton
        }
    }

    init {
        dialog.setOnDismissListener {
            binding = null
        }
        binding?.let {
            it.tvTitle.text = textActionBar
            it.igbBack.setOnClickListener {
                dialog.dismiss()
            }
            it.btnRight.setOnClickListener {
                _listenerRightChoose?.invoke()
            }
            it.btnLeft.setOnClickListener {
                _listenerLeftChoose?.invoke()
            }
        }
    }

    fun show(textMessage: String) {
        binding?.tvMassage?.text = textMessage
        dialog.show()
    }
}