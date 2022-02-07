package com.example.dictionary.data.model

import com.example.dictionary.utils.other.sendOneParametreBlock

open class Event<T>(private val content: T, val block: sendOneParametreBlock<T>? = null) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}
