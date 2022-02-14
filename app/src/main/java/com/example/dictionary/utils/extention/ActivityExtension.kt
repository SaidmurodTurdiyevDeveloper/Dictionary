package com.example.dictionary.utils.extention

import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.changeStatusBarColor(color: Int){
    window.statusBarColor=color
}

fun AppCompatActivity.changeNavigationBarColor(color: Int){
    window.navigationBarColor = color
}