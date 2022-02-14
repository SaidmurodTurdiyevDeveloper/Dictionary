package com.example.dictionary.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.dictionary.BuildConfig
import com.example.dictionary.data.source.local.shared.SharedDatabese
import com.example.dictionary.utils.other.MyStringObjects
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
        val isNight = SharedDatabese.getInstaces(this).getBoolenData(MyStringObjects.DAY_NIGHT)
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        lateinit var application: Application
    }
}