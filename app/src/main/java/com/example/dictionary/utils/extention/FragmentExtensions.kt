package com.example.dictionary.utils.extention

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.dictionary.data.model.Event

fun Fragment.putArguments(block: Bundle.() -> Unit): Fragment {
    val bundle = arguments ?: Bundle()
    block(bundle)
    arguments = bundle
    return this
}

fun <T> Fragment.loadOnlyOneTimeObserver(data: Event<T>, block: T.() -> Unit) {
    val d = data.getContentIfNotHandled()
    if (d != null) {
        block.invoke(d)
    }
}