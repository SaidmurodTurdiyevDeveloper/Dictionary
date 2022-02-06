package com.example.dictionary.domen.impl

import com.example.dictionary.data.repository.FakeSplashRepository
import com.example.dictionary.domen.UseCaseSplash
import com.example.dictionary.utils.other.Responce
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UseCaseSplashImplamentTest{
    lateinit var repository: FakeSplashRepository
    lateinit var useCaseSplash: UseCaseSplash

    @Before
    fun setUp() {
        repository = FakeSplashRepository()
        useCaseSplash = UseCaseSplashImplament(repository)
    }

    @Test
    fun `init first time`() = runBlockingTest {
        repository.setIsfirst(true)
        useCaseSplash.init().collectLatest {
            when (it) {
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Success -> {
                    Truth.assertThat(it.data).isTrue()
                }
                else -> {
                    Truth.assertThat(false).isTrue()
                }
            }
        }
    }

    @Test
    fun `init another time`() = runBlockingTest {
        repository.setIsfirst(false)
        useCaseSplash.init().collectLatest {
            when (it) {
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Success -> {
                    Truth.assertThat(it.data).isFalse()
                }
                else -> {
                    Truth.assertThat(false).isTrue()
                }
            }
        }
    }
}