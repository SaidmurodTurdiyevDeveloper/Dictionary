package com.example.dictionary.ui.screens


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dictionary.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MyNewWords)
        setContentView(R.layout.activity_main)
    }
}