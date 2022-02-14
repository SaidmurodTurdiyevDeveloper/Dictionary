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
//@RunWith(AndroidJUnit4::class)
/**
 * RunWith(AndroidJunit4::class) replace with HiltAndroidTest
 */
@HiltAndroidTest
class SplashRoomDatabaseDaoTest {
    /**
     * Add this rule for hilt
     * */
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: DictionaryDatabase
    private lateinit var daoSplash: SplashRoomDatabaseDao
    private lateinit var daoWord: WordRoomDatabaseDao

    @Before
    fun setUp() {
        //database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), DictionaryDatabase::class.java).allowMainThreadQueries().build()
        hiltRule.inject()
        daoSplash = database.getSplashDao()
        daoWord = database.getWordsListDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun getListEmptyListWord_returnAllEmptyWords() = runBlockingTest {
        val data = insertData(1)
        daoSplash.getEmptyWords()
        assertThat(daoSplash.getEmptyWords()).contains(data)
    }

    @Test
    fun deleteEmptyWordList_returnRemoveAllEmptyWords() = runBlockingTest {
        val data = insertData(1)
        val item = daoSplash.deleteAll(daoSplash.getEmptyWords())
        val list = daoSplash.getEmptyWords()
        assertThat(item).isGreaterThan(0)
        assertThat(list).doesNotContain(data)
    }

    private suspend fun insertData(id: Long): WordEntity {
        val word = WordEntity(id, "Hello", "Salom", "Hello World", -1, 0)
        daoWord.insert(word)
        return word
    }
}