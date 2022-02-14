package com.example.dictionary.data.source.local.room.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.dictionary.data.source.local.room.DictionaryDatabase
import com.example.dictionary.data.source.local.room.dao.dictionaries.ArchiveRoomDatabaseDao
import com.example.dictionary.data.source.local.room.dao.dictionaries.MainRoomDatabaseDao
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
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
class ArxiveRoomDatabaseDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get: Rule
    var instanttTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: DictionaryDatabase

    private lateinit var dao: ArchiveRoomDatabaseDao
    private lateinit var mainDao: MainRoomDatabaseDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = database.getArchiveDao()
        mainDao = database.getMainDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun getListOfArchive_returnSuccesList() = runBlockingTest {
        val data = insertData(1)
        val list = dao.getArxiveList()
        assertThat(list).contains(data)
    }

    @Test
    fun deleteFromArchive_returnSuccesDelete() = runBlockingTest {
        val data = insertData(1)
        dao.delete(data)
        val list = dao.getArxiveList()
        assertThat(list).doesNotContain(data)
    }

    @Test
    fun deleteAllFromArchive_returnSuccelDelete() = runBlockingTest {
        insertData(1)
        insertData(2)
        insertData(3)
        dao.deleteAll(dao.getArxiveList())
        assertThat(dao.getArxiveList()).isEmpty()
    }

    @Test
    fun updateDataInArchive_returnSuccesUpdate() = runBlockingTest {
        val data = insertData(1)
        data.isDelete = 0
        dao.update(data)
        assertThat(dao.getArxiveList()).doesNotContain(data)
    }

    @Test
    fun getArchiveListSize_returnSuccesSize() = runBlockingTest {
        insertData(1)
        insertData(2)
        assertThat(dao.getSize()).isEqualTo(2)
    }

    private suspend fun insertData(id: Long): DictionaryEntity {
        val dictionary = DictionaryEntity(id, "Dictionary One", "Lesson one", 0, 1, 1, 0)
        mainDao.insert(dictionary)
        dictionary.isDelete = 1
        mainDao.update(dictionary)
        return dictionary
    }
}