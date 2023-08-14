package com.imannuel.petabencana.data.repository

import android.accounts.NetworkErrorException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.imannuel.petabencana.UrunDayaDummyData
import com.imannuel.petabencana.data.model.Properties
import com.imannuel.petabencana.data.model.UrunDaya
import com.imannuel.petabencana.data.source.local.room.UrunDayaDAO
import com.imannuel.petabencana.data.source.remote.ApiServices
import com.imannuel.petabencana.ui.saved.PropertiesDummyData
import com.imannuel.petabencana.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.io.IOException
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class UrunDayaRepositoryTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiServices: ApiServices
    @Mock
    private lateinit var urunDayaDAO: UrunDayaDAO

    private lateinit var urunDayaRepository: UrunDayaRepository

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        urunDayaRepository = UrunDayaRepository(apiServices, urunDayaDAO)
    }

    @After
    fun tearDown() {
        testScope.cleanupTestCoroutines()
    }


    @Test
    fun `getUrunDaya returns success`() = runBlocking {
        val admin = "admin"
        val disaster = "disaster"
        val timePeriod = 1
        val testData = UrunDayaDummyData.dummyUrunDaya

        Mockito.`when`(apiServices.getLatestReports(admin, disaster, timePeriod)).thenReturn(testData)

        val result = urunDayaRepository.getUrunDaya(admin, disaster, timePeriod)

        result.collect{
            when(it){
                is Resource.Success -> {
                    assertTrue(it is Resource.Success)
                    assertEquals(testData, it.data)
                }
                else -> {}
            }
        }
    }


    @Test
    fun `getUrunDaya returns error`() = runBlocking {
        val admin = "admin"
        val disaster = "disaster"
        val timePeriod = 1
        val expectedErrorMessage = "Server down"

        val error = RuntimeException(expectedErrorMessage)
        Mockito.`when`(apiServices.getLatestReports(admin, disaster, timePeriod)).thenThrow(error)

        val result = urunDayaRepository.getUrunDaya(admin, disaster, timePeriod)

        result.collect{
            when(it){
                is Resource.Error -> {
                    assertTrue(it is Resource.Error)
                }
                else -> {}
            }
        }

    }

    @Test
    fun `getAllSavedUrunDaya return expected data`() = runBlocking {
        val dummyData = listOf<Properties>(
            PropertiesDummyData.sampleProperties
        )

        val expectedReturn = MutableLiveData<List<Properties>>()
        expectedReturn.value = dummyData

        Mockito.`when`(urunDayaDAO.getAllSavedUrunDaya()).thenReturn(expectedReturn)

        Mockito.`when`(urunDayaRepository.getAllSavedUrunDaya()).thenReturn(expectedReturn)

        val savedUrunDaya = urunDayaRepository.getAllSavedUrunDaya()

        assertEquals(dummyData, savedUrunDaya.value)
    }

    @Test
    fun `test isUrunDayaExist run correctly`() = runBlockingTest {
        val id = "id"

        val expectedReturn = MutableLiveData<Int>()
        expectedReturn.value = 1

        Mockito.`when`(urunDayaDAO.isUrunDayaExist(id)).thenReturn(expectedReturn)

        urunDayaRepository.isUrunDayaExist(id)

        Mockito.verify(urunDayaDAO).isUrunDayaExist(id)
    }


    @Test
    fun `test saveUrunDaya run correctly`() = runBlockingTest {
        val dummyData = PropertiesDummyData.sampleProperties

        Mockito.`when`(urunDayaDAO.saveUrunDaya(dummyData)).thenReturn(1)

        urunDayaRepository.saveUrunDaya(dummyData)

        Mockito.verify(urunDayaDAO).saveUrunDaya(dummyData)
    }

    @Test
    fun `test deleteSavedUrunDaya run correctly`() = runBlockingTest {
        val id = "id"

        Mockito.`when`(urunDayaDAO.deleteUrunDaya(id)).thenReturn(Unit)

        urunDayaRepository.deleteUrunDaya(id)

        Mockito.verify(urunDayaDAO).deleteUrunDaya(id)
    }




}