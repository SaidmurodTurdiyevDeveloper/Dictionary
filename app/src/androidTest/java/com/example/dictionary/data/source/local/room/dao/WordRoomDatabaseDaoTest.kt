package com.example.dictionary.data.source.local.room.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.dictionary.data.source.local.room.DictionaryDatabase
import com.example.dictionary.data.source.local.room.dao.word.WordRoomDatabaseDao
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
class WordRoomDatabaseDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Inject
    @Named("test_db")
    lateinit var database: DictionaryDatabase
    private lateinit var wordDao: WordRoomDatabaseDao

    @Before
    fun setUp() {
        hiltRule.inject()
        wordDao = database.getWordsListDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertData_returnSuccesInsert() = runBlockingTest {
        val word = insertData(1)
        assertThat(wordDao.getList(1)).contains(word)
    }

    @Test
    fun updateData_returnSuccesUpdate() = runBlockingTest {
        val word = insertData(1)
        word.wordOne = "Assalom"
        word.wordTwo = "good morning"
        wordDao.update(word)
        assertThat(wordDao.getList(1)).contains(word)
    }

    @Test
    fun deleteData_returnSuccesDelete() = runBlockingTest {
        val word = insertData(1)
        wordDao.delete(word)
        assertThat(wordDao.getList(1)).doesNotContain(word)
    }

    @Test
    fun deleteAllData_returnSuccesDelete() = runBlockingTest {
        val list = ArrayList<WordEntity>()
        list.add(insertData(1))
        list.add(insertData(2))
        list.add(insertData(3))
        wordDao.deleteAll(list)
        assertThat(wordDao.getList(1)).containsNoneIn(list)
    }

    @Test
    fun getListOfWords_returnSuccesList() = runBlockingTest {
        val list = ArrayList<WordEntity>()
        list.add(insertData(1))
        list.add(insertData(2))
        list.add(insertData(3))
        list.forEach {
            assertThat(wordDao.getList(1)).contains(it)
        }
    }


    private suspend fun insertData(id: Long): WordEntity {
        val word = WordEntity(id, "Hello", "Salom", "Hello World", 1, 0)
        wordDao.insert(word)
        return word
    }
}