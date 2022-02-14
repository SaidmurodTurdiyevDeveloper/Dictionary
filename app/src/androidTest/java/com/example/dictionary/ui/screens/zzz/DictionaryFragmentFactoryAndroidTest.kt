package com.example.dictionary.ui.screens.zzz

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.dictionary.data.repository.dictionary.FakeArchiveRepositoryAndroidTest
import com.example.dictionary.data.repository.dictionary.FakeMainRepositoryAndroidTest
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.domen.impl.dictionary.UseCaseArchiveImplement
import com.example.dictionary.domen.impl.dictionary.UseCaseMainImplament
import com.example.dictionary.ui.screens.dictionary.FragmentArchive
import com.example.dictionary.ui.screens.dictionary.FragmentMain
import com.example.dictionary.ui.viewModel.impl.dictionary.ViewModelArchive
import com.example.dictionary.ui.viewModel.impl.dictionary.ViewModelMain
import javax.inject.Inject

class DictionaryFragmentFactoryAndroidTest @Inject constructor() : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            FragmentMain::class.java.name -> {
                val testUseCase = UseCaseMainImplament(FakeMainRepositoryAndroidTest())
                val testViewModel = ViewModelMain(testUseCase)
                FragmentMain(testViewModel)
            }
            FragmentArchive::class.java.name -> {
                val repository = FakeArchiveRepositoryAndroidTest()
                repository.addDictionary(DictionaryEntity(-1, "Dictionary", "Info", 100, 1, 2, 1))
                val testUseCase = UseCaseArchiveImplement(repository)
                val testViewModel = ViewModelArchive(testUseCase)
                FragmentArchive(testViewModel)
            }
            else -> super.instantiate(classLoader, className)
        }
    }
}