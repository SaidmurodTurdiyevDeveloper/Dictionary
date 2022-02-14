package com.example.dictionary.contracts

import androidx.lifecycle.LiveData
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.WordEntity

interface ContractSplash {
    interface Model {
        suspend fun getIsfFirst(): Boolean
        suspend fun getEmptyWord(): List<WordEntity>
        suspend fun deleteAllEmpty(list: List<WordEntity>): Int
    }

    interface ViewModel {
        val openChooselanguageLiveData: LiveData<Unit>
        val openMainScreenLiveData: LiveData<Unit>
        val loadingScreenLiveData: LiveData<Event<Boolean>>
        val showMessage: LiveData<Event<String>>
        fun init()
    }
}