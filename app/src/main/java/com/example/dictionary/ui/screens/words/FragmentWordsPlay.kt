package com.example.dictionary.ui.screens.words

import android.graphics.drawable.AnimationDrawable
import androidx.fragment.app.Fragment
import com.example.dictionary.R

class FragmentWordsPlay:Fragment(R.layout.fragment_words_play) {
    private var anim: AnimationDrawable? = null




    override fun onPause() {
        super.onPause()
        anim?.let {
            if (it.isRunning) {
                it.stop()
            }
        }
    }

    private fun startAnim() {
        anim?.let {
            it.setEnterFadeDuration(2500)
            it.setExitFadeDuration(2500)
            if (!it.isRunning) {
                it.start()
            }
        }
    }
}