package com.example.dictionary.domen.impl.dictionary

import com.example.dictionary.data.repository.dictionary.FakeMainRepository
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.domen.usecase_dictionary.UseCaseMain
import com.example.dictionary.utils.other.Responce
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class UseCaseMainImplamentTest{

    private lateinit var repository: FakeMainRepository
    private lateinit var useCaseMain: UseCaseMain

    @Before
    fun setUp() {
        repository = FakeMainRepository()
        useCaseMain = UseCaseMainImplament(repository)
    }

    @Test
    fun `get learned words caunt when it was 0`() = runBlockingTest {
        repository.setLearningCount(0)
        useCaseMain.getCountLearnedWords().collectLatest {
            Truth.assertThat(it).isEqualTo("0")
        }
    }

    @Test
    fun `get learned words caunt when less than a thousand`() = runBlockingTest {
        repository.setLearningCount(236)
        useCaseMain.getCountLearnedWords().collectLatest {
            Truth.assertThat(it).isEqualTo("236")
        }
    }

    @Test
    fun `get learned words caunt when more than a thousand`() = runBlockingTest {
        repository.setLearningCount(1234)
        useCaseMain.getCountLearnedWords().collectLatest {
            Truth.assertThat(it).isEqualTo("1.23K")
        }
    }

    @Test
    fun `get learned words caunt when more than a thousand and it can div hundred`() = runBlockingTest {
        repository.setLearningCount(1200)
        useCaseMain.getCountLearnedWords().collectLatest {
            Truth.assertThat(it).isEqualTo("1.2K")
        }
    }

    @Test
    fun `get learned words caunt when more than a million`() = runBlockingTest {
        repository.setLearningCount(12345678)
        useCaseMain.getCountLearnedWords().collectLatest {
            Truth.assertThat(it).isEqualTo("12M")
        }
    }

    @Test
    fun `get learned words caunt when more than a billion`() = runBlockingTest {
        repository.setLearningCount(1234567890)
        useCaseMain.getCountLearnedWords().collectLatest {
            Truth.assertThat(it).isEqualTo("More")
        }
    }

    @Test
    fun `dictionary add succesfully`() = runBlockingTest {
        val dictionary = DictionaryEntity(1, "Dictionary", "Coding", 0, 1, 2, 0)
        useCaseMain.addDictionary(dictionary).collectLatest {
            when (it) {
                is Responce.Success -> {
                    Truth.assertThat(it.data).contains(dictionary)
                }
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isEmpty()
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isEmpty()
                }
            }
        }
    }

    @Test
    fun `exist dictionary do not add`() = runBlockingTest {
        val dictionary = DictionaryEntity(1, "Dictionary", "Coding", 0, 1, 2, 0)
        repository.addNewDictionary(dictionary)
        useCaseMain.addDictionary(dictionary).collectLatest {
            when (it) {
                is Responce.Success -> {
                    Truth.assertThat(it.data).contains(dictionary)
                }
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isNotEmpty()
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isEmpty()
                }
            }
        }
    }

    @Test
    fun `get all dictinary when list was empty`() = runBlockingTest {
        useCaseMain.getDictionaryList().collectLatest {
            when (it) {
                is Responce.Success -> {
                    Truth.assertThat(it.data).isEmpty()
                }
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isEmpty()
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isNotEmpty()
                }
            }
        }
    }

    @Test
    fun `get all dictinary when list was not empty`() = runBlockingTest {
        val dictionary = DictionaryEntity(1, "Dictionary", "Coding", 0, 1, 2, 0)
        repository.addNewDictionary(dictionary)
        useCaseMain.getDictionaryList().collectLatest {
            when (it) {
                is Responce.Success -> {
                    Truth.assertThat(it.data).isNotEmpty()
                }
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isEmpty()
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isEmpty()
                }
            }
        }
    }

    @Test
    fun `dictionary update successfully`() = runBlockingTest {
        val dictionary = DictionaryEntity(1, "Dictionary", "Coding", 0, 1, 2, 0)
        repository.addNewDictionary(dictionary)
        val newDictionary = DictionaryEntity(1, "New Dictionary", "New Coding", 0, 1, 2, 0)
        useCaseMain.updateDictionary(dictionary, newDictionary).collectLatest {
            when (it) {
                is Responce.Success -> {
                    Truth.assertThat(it.data).contains(newDictionary)
                }
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isEmpty()
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isEmpty()
                }
            }
        }
    }

    @Test
    fun `dictionary do not update if it was same with old`() = runBlockingTest {
        val dictionary = DictionaryEntity(1, "Dictionary", "Coding", 0, 1, 2, 0)
        repository.addNewDictionary(dictionary)
        val newDictionary = DictionaryEntity(1, "Dictionary", "Coding", 0, 1, 2, 0)
        useCaseMain.updateDictionary(dictionary, newDictionary).collectLatest {
            when (it) {
                is Responce.Success -> {
                    Truth.assertThat(it.data).doesNotContain(newDictionary)
                }
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isNotEmpty()
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isEmpty()
                }
            }
        }
    }

    @Test
    fun `dictionary remove successfully`() = runBlockingTest {
        val dictionary = DictionaryEntity(1, "Dictionary", "Coding", 0, 1, 2, 0)
        val dictionary1 = DictionaryEntity(2, "Dictionary1", "Coding1", 0, 1, 2, 0)
        repository.addNewDictionary(dictionary)
        repository.addNewDictionary(dictionary1)
        useCaseMain.removeDictionaryActive(dictionary).collectLatest {
            when (it) {
                is Responce.Success -> {
                    Truth.assertThat(it.data).doesNotContain(dictionary)
                }
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isEmpty()
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isEmpty()
                }
            }
        }
    }

    @Test
    fun `dictionary remove and any dictionary does not return`() = runBlockingTest {
        val dictionary = DictionaryEntity(1, "Dictionary", "Coding", 0, 1, 2, 0)
        repository.addNewDictionary(dictionary)
        useCaseMain.removeDictionaryActive(dictionary).collectLatest {
            when (it) {
                is Responce.Success -> {
                    Truth.assertThat(it.data).doesNotContain(dictionary)
                }
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isEmpty()
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isNotEmpty()
                }
            }
        }
    }

    @Test
    fun `dictionary remove selected list successfully`() = runBlockingTest {
        val dictionary = DictionaryEntity(1, "Dictionary", "Coding", 0, 1, 2, 0)
        val dictionary1 = DictionaryEntity(2, "Dictionary1", "Coding1", 0, 1, 2, 0)
        val dictionary2 = DictionaryEntity(3, "Dictionary2", "Coding2", 0, 1, 2, 0)
        repository.addNewDictionary(dictionary)
        repository.addNewDictionary(dictionary1)
        repository.addNewDictionary(dictionary2)
        useCaseMain.selectItem(dictionary).launchIn(TestCoroutineScope())
        useCaseMain.selectItem(dictionary1).launchIn(TestCoroutineScope())
        useCaseMain.selectItem(dictionary2).launchIn(TestCoroutineScope())
        useCaseMain.deleteSelectedList().collectLatest {
            when (it) {
                is Responce.Success -> {
                    Truth.assertThat(it.data).containsNoneIn(arrayListOf(dictionary, dictionary1, dictionary2))
                }
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isNotEmpty()
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isEmpty()
                }
            }
        }
    }

    @Test
    fun `dictionary selected item`() = runBlockingTest {
        val dictionary = DictionaryEntity(1, "Dictionary", "Coding", 0, 1, 2, 0)
        repository.addNewDictionary(dictionary)
        useCaseMain.selectItem(dictionary).collectLatest {
            when (it) {
                is Responce.Success -> {
                    Truth.assertThat(it.data.first().isSelect).isTrue()
                }
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isNotEmpty()
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isEmpty()
                }
            }
        }
    }

    @Test
    fun `dictionary unSelect item`() = runBlockingTest {
        val dictionary = DictionaryEntity(1, "Dictionary", "Coding", 0, 1, 2, 0)
        repository.addNewDictionary(dictionary)
        useCaseMain.selectItem(dictionary).launchIn(TestCoroutineScope())
        useCaseMain.selectItem(dictionary).collectLatest {
            when (it) {
                is Responce.Success -> {
                    Truth.assertThat(it.data.first().isSelect).isFalse()
                }
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isNotEmpty()
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isEmpty()
                }
            }
        }
    }

    @Test
    fun `dictionary selected all item`() = runBlockingTest {
        val dictionary = DictionaryEntity(1, "Dictionary", "Coding", 0, 1, 2, 0)
        val dictionary1 = DictionaryEntity(2, "Dictionary1", "Coding1", 0, 1, 2, 0)
        val dictionary2 = DictionaryEntity(3, "Dictionary2", "Coding2", 0, 1, 2, 0)
        repository.addNewDictionary(dictionary)
        repository.addNewDictionary(dictionary1)
        repository.addNewDictionary(dictionary2)
        useCaseMain.selectAllItem(true).collectLatest {
            when (it) {
                is Responce.Success -> {
                    val list = it.data.filter { it.isSelect }
                    Truth.assertThat(list.size).isEqualTo(it.data.size)
                }
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isNotEmpty()
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isEmpty()
                }
            }
        }
    }

    @Test
    fun `dictionary unSelected all item`() = runBlockingTest {
        val dictionary = DictionaryEntity(1, "Dictionary", "Coding", 0, 1, 2, 0)
        val dictionary1 = DictionaryEntity(2, "Dictionary1", "Coding1", 0, 1, 2, 0)
        val dictionary2 = DictionaryEntity(3, "Dictionary2", "Coding2", 0, 1, 2, 0)
        repository.addNewDictionary(dictionary)
        repository.addNewDictionary(dictionary1)
        repository.addNewDictionary(dictionary2)
        useCaseMain.selectAllItem(false).collectLatest {
            when (it) {
                is Responce.Success -> {
                    val list = it.data.filter { it.isSelect }
                    Truth.assertThat(list.size).isEqualTo(0)
                }
                is Responce.Loading -> {
                    Truth.assertThat(true).isTrue()
                }
                is Responce.Message -> {
                    Truth.assertThat(it.message).isNotEmpty()
                }
                is Responce.Error -> {
                    Truth.assertThat(it.error).isEmpty()
                }
            }
        }
    }

    @Test
    fun `changing night mode to day`() = runBlockingTest {
        repository.setDayOrNight(false)
        useCaseMain.changeDayNight().collectLatest {
            Truth.assertThat(it).isTrue()
        }
    }

    @Test
    fun `changing day mode to night`() = runBlockingTest {
        repository.setDayOrNight(true)
        useCaseMain.changeDayNight().collectLatest {
            Truth.assertThat(it).isFalse()
        }
    }

    @Test
    fun `get selected item count`() = runBlockingTest {
        val dictionary = DictionaryEntity(1, "Dictionary", "Coding", 0, 1, 2, 0)
        val dictionary1 = DictionaryEntity(2, "Dictionary1", "Coding1", 0, 1, 2, 0)
        val dictionary2 = DictionaryEntity(3, "Dictionary2", "Coding2", 0, 1, 2, 0)
        repository.addNewDictionary(dictionary)
        repository.addNewDictionary(dictionary1)
        repository.addNewDictionary(dictionary2)
        useCaseMain.selectItem(dictionary).launchIn(TestCoroutineScope())
        useCaseMain.selectItem(dictionary1).launchIn(TestCoroutineScope())
        Truth.assertThat(useCaseMain.selectedItemcount()).isEqualTo(2)
    }
}