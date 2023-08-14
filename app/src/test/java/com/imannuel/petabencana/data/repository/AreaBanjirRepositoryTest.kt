package com.imannuel.petabencana.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.imannuel.petabencana.AreaBanjirDummyData
import com.imannuel.petabencana.UrunDayaDummyData
import com.imannuel.petabencana.data.source.local.room.UrunDayaDAO
import com.imannuel.petabencana.data.source.remote.ApiServices
import com.imannuel.petabencana.utils.Resource
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.RuntimeException

class AreaBanjirRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiServices: ApiServices

    private lateinit var areaBanjirRepository: AreaBanjirRepository

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        areaBanjirRepository = AreaBanjirRepository(apiServices)
    }

    @After
    fun tearDown() {
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `getAreaBanjir returns success`() = runBlocking {
        val testData = AreaBanjirDummyData.dummyAreaBanjir

        Mockito.`when`(apiServices.getRealTimeFlood(1, "ID-JK")).thenReturn(testData)

        val result = areaBanjirRepository.getAreaBanjir()

        result.collect {
            when (it) {
                is Resource.Success -> {
                    assertTrue(it is Resource.Success)
                    assertEquals(testData, it.data)
                }

                else -> {}
            }
        }
    }

    @Test
    fun `getAreaBanjir returns error`() = runBlocking {
        val expectedErrorMessage = "Server down"
        val error = RuntimeException(expectedErrorMessage)

        Mockito.`when`(apiServices.getRealTimeFlood()).thenThrow(error)

        val result = areaBanjirRepository.getAreaBanjir()

        result.collect {
            when (it) {
                is Resource.Success -> {
                    assertTrue(it is Resource.Error)
                }

                else -> {}
            }
        }
    }


}