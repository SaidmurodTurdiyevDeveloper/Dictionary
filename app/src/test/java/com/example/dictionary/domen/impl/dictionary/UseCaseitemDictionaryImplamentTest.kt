package com.example.dictionary.domen.impl.dictionary

import com.example.dictionary.data.repository.dictionary.FakeDictionaryRepository
import com.example.dictionary.domen.usecase_dictionary.UseCaseItemDictionary
import com.example.dictionary.utils.other.MyCountries
import com.example.dictionary.utils.other.Responce
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class UseCaseitemDictionaryImplamentTest{
    lateinit var repository: FakeDictionaryRepository
    lateinit var useCase: UseCaseItemDictionary

    @Before
    fun setUp() {
        repository = FakeDictionaryRepository()
        useCase = UseCaseitemDictionaryImplament(repository)
    }

    @Test
    fun `get dictionary with id`() = runBlockingTest {
        useCase.getDictionary(1).collectLatest {
            when (it) {
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Success -> {
                    Truth.assertThat(it.data.id).isEqualTo(1)
                }
                else -> {
                    Truth.assertThat(true).isFalse()
                }
            }
        }
    }

    @Test
    fun `get another dictionary with id`() = runBlockingTest {
        useCase.getDictionary(2).collectLatest {
            when (it) {
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Success -> {
                    Truth.assertThat(it.data.id).isNotEqualTo(2)
                }
                else -> {
                    Truth.assertThat(true).isFalse()
                }
            }
        }
    }

    @Test
    fun `get learn pracent with id`() = runBlockingTest {
        useCase.getDictionaryLearnCount(2).collectLatest {
            when (it) {
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Success -> {
                    Truth.assertThat(it.data).isEqualTo("23/25")
                }
                else -> {
                    Truth.assertThat(true).isFalse()
                }
            }
        }
    }

    @Test
    fun `get cauntry with position`() = runBlockingTest {
        val countries = MyCountries()
        val firstCountry = 0
        val cauntry = useCase.getCountryWithId(firstCountry)
        Truth.assertThat(cauntry).isEqualTo(countries.getCountries()[firstCountry])
    }
}