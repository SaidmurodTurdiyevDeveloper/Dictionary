package com.example.dictionary.domen.impl.dictionary

import com.example.dictionary.data.repository.dictionary.FakeArchiveRepository
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.domen.usecase_dictionary.UseCaseArchive
import com.example.dictionary.utils.other.Responce
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UseCaseArchiveImplementTest {
    lateinit var repository: FakeArchiveRepository
    lateinit var useCase: UseCaseArchive

    @Before
    fun setUp() {
        repository = FakeArchiveRepository()
        useCase = UseCaseArchiveImplement(repository)
    }

    @Test
    fun `get all archive elements`() = runBlockingTest {
        repository.addDictionary(DictionaryEntity(1, "Dictionary", "Nothing", 0, 1, 2, 0))
        repository.addDictionary(DictionaryEntity(2, "Dictionary second", "Nothing", 9, 2, 3, 1))
        useCase.getAllArxiveList().collectLatest {
            when (it) {
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Success -> {
                    Truth.assertThat(it.data).isNotEmpty()
                }
                else -> {
                    Truth.assertThat(true).isFalse()
                }
            }
        }
    }


    @Test
    fun `get empty list`() = runBlockingTest {
        useCase.getAllArxiveList().collectLatest {
            when (it) {
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Success -> {
                    Truth.assertThat(it.data).isEmpty()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isNotEmpty()
                }
                else -> {
                    Truth.assertThat(true).isFalse()
                }
            }
        }
    }

    @Test
    fun `delete element`() = runBlockingTest {
        val data = DictionaryEntity(1, "Dictionary", "Nothing", 0, 1, 2, 0)
        val data2 = DictionaryEntity(2, "Dictionary Second", "Nothing", 0, 1, 2, 0)
        repository.addDictionary(data)
        repository.addDictionary(data2)
        useCase.delete(data).collectLatest {
            when (it) {
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Success -> {
                    Truth.assertThat(it.data).doesNotContain(data)
                }
                else -> {
                    Truth.assertThat(true).isFalse()
                }
            }
        }
    }

    @Test
    fun `delete another element`() = runBlockingTest {
        val data = DictionaryEntity(1, "Dictionary", "Nothing", 0, 1, 2, 0)
        val data2 = DictionaryEntity(2, "Dictionary Second", "Nothing", 0, 1, 2, 0)
        repository.addDictionary(data)
        useCase.delete(data2).collectLatest {
            when (it) {
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Success -> {
                    Truth.assertThat(it.data).doesNotContain(data2)
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isNotEmpty()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isEmpty()
                }
            }
        }
    }

    @Test
    fun `delete element return empty list`() = runBlockingTest {
        val data = DictionaryEntity(1, "Dictionary", "Nothing", 0, 1, 2, 0)
        repository.addDictionary(data)
        useCase.delete(data).collectLatest {
            when (it) {
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Success -> {
                    Truth.assertThat(it.data).isEmpty()
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isEmpty()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isNotEmpty()
                }
            }
        }
    }

    @Test
    fun `update elements`() = runBlockingTest {
        val data = DictionaryEntity(1, "Dictionary", "Nothing", 0, 1, 2, 0)
        val data2 = DictionaryEntity(2, "Dictionary Second", "Nothing", 0, 1, 2, 0)
        repository.addDictionary(data)
        repository.addDictionary(data2)
        useCase.update(data).collectLatest {
            when (it) {
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Success -> {
                    Truth.assertThat(it.data).doesNotContain(data)
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isEmpty()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isEmpty()
                }
            }
        }
    }

    @Test
    fun `delete list`() = runBlockingTest {
        val data = DictionaryEntity(1, "Dictionary", "Nothing", 0, 1, 2, 0)
        val data2 = DictionaryEntity(2, "Dictionary Second", "Nothing", 0, 1, 2, 0)
        repository.addDictionary(data)
        repository.addDictionary(data2)
        useCase.deleteAll().collectLatest {
            when (it) {
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Success -> {
                    Truth.assertThat(it.data).isEmpty()
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isEmpty()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isNotEmpty()
                }
            }
        }
    }

    @Test
    fun `get count`() = runBlockingTest {
        val data = DictionaryEntity(1, "Dictionary", "Nothing", 0, 1, 2, 0)
        val data2 = DictionaryEntity(2, "Dictionary Second", "Nothing", 0, 1, 2, 0)
        repository.addDictionary(data)
        repository.addDictionary(data2)
        val result = useCase.getSize()
        Truth.assertThat(result).isEqualTo(2)
    }
}