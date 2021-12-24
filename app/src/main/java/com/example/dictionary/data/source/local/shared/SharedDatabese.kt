package com.example.dictionary.data.source.local.shared

import android.annotation.SuppressLint
import android.content.Context

class SharedDatabese(context: Context) {
    private var data =
        context.getSharedPreferences("MySharedDatabaseDictionary", Context.MODE_PRIVATE)
    private var editor = data.edit()

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: SharedDatabese? = null
        fun getInstaces(context: Context): SharedDatabese {
            return instance ?: let {
                val t = SharedDatabese(context)
                instance = t
                t
            }
        }
    }

    fun setBoolenData(myStringObjectData: String, data: Boolean) {
        editor.putBoolean(myStringObjectData, data)
        editor.apply()
    }

    fun getBoolenData(myStringObjectData: String): Boolean =
        this.data.getBoolean(myStringObjectData, false)

    fun setIntData(myStringObjectData: String, data: Int) {
        editor.putInt(myStringObjectData, data)
        editor.apply()
    }

    fun getIntData(myStringObjectData: String): Int =
        this.data.getInt(myStringObjectData, -1)

}