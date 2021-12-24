package com.example.dictionary.utils.extention

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(resId:Int):View{
    return LayoutInflater.from(context).inflate(resId,this,false)
}