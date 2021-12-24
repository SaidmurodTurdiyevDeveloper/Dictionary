package com.example.dictionary.utils.extention

import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * Created by Sherzodbek Muhammadiev on 28.01.2020
 */


/**
 * Extension method to set different color for substring TextView.
 */
fun TextView.setColorOfSubstring(substring: String, color: Int) {
    try {
        val spannable = android.text.SpannableString(text)
        val start = text.indexOf(substring)
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, color)), start, start + substring.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        text = spannable
    } catch (e: Exception) {
        e.printStackTrace()
//        Timber.d("exception in setColorOfSubstring, text=$text, substring=$substring")
    }
}
