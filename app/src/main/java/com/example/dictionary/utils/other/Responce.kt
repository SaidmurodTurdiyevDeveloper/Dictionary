package com.example.dictionary.utils.other

sealed class Responce<T> {
    class Loading<T>(var cond:Boolean) : Responce<T>()
    class Success<T>(var data: T) : Responce<T>()
    class Message<T>(var message: String) : Responce<T>()
    class Error<T>(var error: String) : Responce<T>()
}