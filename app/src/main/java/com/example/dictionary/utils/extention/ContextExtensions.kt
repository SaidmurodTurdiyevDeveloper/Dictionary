package com.example.dictionary.utils.extention

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: String) = if (text != "") Toast.makeText(this, text, Toast.LENGTH_SHORT).show() else null
