package com.imannuel.petabencana.data.source.local.room

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.imannuel.petabencana.PropertiesDummyData
import com.imannuel.petabencana.data.model.Properties
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
@SmallTest
@ExperimentalCoroutinesApi
class UrunDayaDAOTest {

    private lateinit var database: UrunDayaDatabase
    private lateinit var dao: UrunDayaDAO

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, UrunDayaDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.urunDayaDao()
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertAndSaved() = runTest {
        val dummyData = listOf<Properties>(
            PropertiesDummyData.sampleProperties
        )


        dummyData.forEach {
            database.urunDayaDao().saveUrunDaya(it)
        }

        val savedUrunDaya = dao.getAllSavedUrunDaya().getOrAwaitValue()

        assertThat(savedUrunDaya, `is`(dummyData))
    }

    @Test
    fun isExist() = runTest {
        val dummyData = listOf<Properties>(
            PropertiesDummyData.sampleProperties
        )

        dummyData.forEach {
            database.urunDayaDao().saveUrunDaya(it)
        }

        val isExist = dao.isUrunDayaExist(dummyData.first().pkey ?: "").getOrAwaitValue()
        assertThat(1, `is`(isExist))

    }

    @Test
    fun delete() = runTest {
        val urunDaya = PropertiesDummyData.sampleProperties
        dao.saveUrunDaya(urunDaya)

        dao.deleteUrunDaya(urunDaya.pkey ?: "")

        val isExist = dao.isUrunDayaExist(urunDaya.pkey ?: "").getOrAwaitValue()

        assertEquals(0, isExist)
    }


}

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {

        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}