package com.example.dictionary.data.source.local.room.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.dictionary.data.source.local.room.DictionaryDatabase
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
class MainRoomDatabaseDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Inject
    @Named("test_db")
    lateinit var database: DictionaryDatabase
    private lateinit var dao: MainRoomDatabaseDao
    private lateinit var wordDao: WordRoomDatabaseDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = database.getMainDao()
        wordDao = database.getWordsListDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertDictionary_returnSuccesAdd() = runBlockingTest {
        val dictionary = insertData(1)
        assertThat(dao.getDictionaries(1, 2)).contains(dictionary)
    }

    @Test
    fun removeDictionary_returnSuccesRemove() = runBlockingTest {
        val dictionary = insertData(1)
        dao.delete(dictionary)
        assertThat(dao.getDictionaries(1, 2)).doesNotContain(dictionary)
    }

    @Test
    fun deleteAllDictionary_returSuccesDelete() = runBlockingTest {
        val list = ArrayList<DictionaryEntity>()
        list.add(insertData(1))
        list.add(insertData(2))
        list.add(insertData(3))
        dao.deleteAll(list)
        assertThat(dao.getDictionaries(1, 2)).containsNoneIn(list)
    }

    @Test
    fun updateData_returnSuccesEdit() = runBlockingTest {
        val data = insertData(1)
        data.name = "New Dictionary"
        dao.update(data)
        assertThat(dao.getDictionaries(1, 2)).contains(data)
    }

    @Test
    fun getListOfDictionary_returnSuccesList() = runBlockingTest {
        val list = ArrayList<DictionaryEntity>()
        list.add(insertData(1))
        list.add(insertData(2))
        list.add(insertData(3))
        list.forEach {
            assertThat(dao.getDictionaries(1, 2)).contains(it)
        }
    }

    @Test
    fun getCountOfLearnedWords_returnSuccesFind() = runBlockingTest {
        wordDao.insert(WordEntity(1, "Hello", "Salom", "Hello World", 1, 4))
        wordDao.insert(WordEntity(2, "Bye", "Xayr", "Good Bye", 1, 2))
        wordDao.insert(WordEntity(3, "Ok", "Yaxshi", "Ok,let`s go", 1, 5))
        assertThat(dao.getLearnedCount()).isEqualTo(2)
    }

    private suspend fun insertData(id: Long): DictionaryEntity {
        val dictionary = DictionaryEntity(id, "Dictionary One", "Lesson one", 0, 1, 2, 0)
        dao.insert(dictionary)
        return dictionary
    }
}