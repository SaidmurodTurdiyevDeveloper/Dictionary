package com.example.dictionary.data.source.local.room.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.dictionary.data.source.local.room.DictionaryDatabase
import com.example.dictionary.data.source.local.room.dao.dictionaries.DictionaryItemRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.MainRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.word.WordRoomDatabaseDao
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.data.source.local.room.entity.WordEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class DictionaryItemRoomDatabaseDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: DictionaryDatabase
    private lateinit var dao: DictionaryItemRoomDatabaseDao
    private lateinit var mainDao: MainRoomDatabaseDao
    private lateinit var wordDao: WordRoomDatabaseDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = database.getDictionaryItemDao()
        mainDao = database.getMainDao()
        wordDao = database.getWordsListDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun getDataWithId_returnSuccesFind() = runBlockingTest {
        val data = insertData(1)
        assertThat(dao.getDictionaryById(1)).isEqualTo(data)
    }

    @Test
    fun updateData_returnSuccesUpdate() = runBlockingTest {
        val data = insertData(1)
        data.languageIdOne = 3
        data.languageIdTwo = 4
        dao.update(data)
        assertThat(dao.getDictionaryById(1)).isEqualTo(data)
    }

    @Test
    fun getCountOfDictionaryWords_returnSucces() = runBlockingTest {
        val word = WordEntity(1, "Hello", "Salom", "Hello World", 1, 3)
        val word1 = WordEntity(2, "Good", "Yaxshi", "Good job", 1, 2)
        val word2 = WordEntity(3, "Perfect", "Mukammal", "it is perfect", 1, 5)
        wordDao.insert(word)
        wordDao.insert(word1)
        wordDao.insert(word2)
        assertThat(dao.getWordsCountOfDictinary(1)).isEqualTo(3)
    }
    @Test
    fun getLearnedWordsOfDictionary_returnSucces() = runBlockingTest {
        val word = WordEntity(1, "Hello", "Salom", "Hello World", 1, 5)
        val word1 = WordEntity(2, "Good", "Yaxshi", "Good job", 1, 2)
        val word2 = WordEntity(3, "Perfect", "Mukammal", "it is perfect", 1, 4)
        wordDao.insert(word)
        wordDao.insert(word1)
        wordDao.insert(word2)
        assertThat(dao.geDictionaryWordsLearnCount(1)).isEqualTo(2)
    }

    private suspend fun insertData(id: Long): DictionaryEntity {
        val dictionary = DictionaryEntity(id, "Dictionary One", "Lesson one", 0, 1, 1, 0)
        mainDao.insert(dictionary)
        return dictionary
    }
}






