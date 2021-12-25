package com.example.dictionary.data.model

open class Event<out T>(private val content: T, private val block: (() -> Unit)? = null) {

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
