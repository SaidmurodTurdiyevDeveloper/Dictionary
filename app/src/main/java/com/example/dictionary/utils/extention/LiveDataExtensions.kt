package com.example.dictionary.utils.extention

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

fun <T> MediatorLiveData<*>.addSourceDisposable(liveData: LiveData<T>, onChange: (T) -> Unit): MediatorLiveData<*> {
    addSource(liveData) {
        onChange(it)
        removeSource(liveData)
    }
    return this
}

fun <T> MediatorLiveData<*>.addSourceDisposableNoAction(liveData: LiveData<T>): MediatorLiveData<*> {
    addSource(liveData) {
        removeSource(liveData)
    }
    return this
}
fun <T> LiveData<T>.observerOnce(lifecycleOwner: LifecycleOwner,observer: Observer<T>){
    observe(lifecycleOwner,object :Observer<T>{
        override fun onChanged(t: T) {
            observer.onChanged(t)
            removeObserver(this)
        }

    })
}