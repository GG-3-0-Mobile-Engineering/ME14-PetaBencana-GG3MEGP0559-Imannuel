package com.imannuel.petabencana.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.imannuel.petabencana.UrunDayaDummyData
import com.imannuel.petabencana.data.model.UrunDaya
import com.imannuel.petabencana.data.repository.UrunDayaRepository
import com.imannuel.petabencana.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var urunDayaRepository: UrunDayaRepository

    lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        homeViewModel = HomeViewModel(urunDayaRepository)
    }

    @Test
    fun `test setArea`() {
        val expectedReturn = "Test Area"
        val testObserver = TestObserver<String>()

        homeViewModel.area.observeForever(testObserver)

        homeViewModel.setArea(expectedReturn)

        assertEquals(expectedReturn, testObserver.observedValue)

        homeViewModel.area.removeObserver(testObserver)
    }

    @Test
    fun `test setDisaster`() {
        val expectedReturn = "Test Disaster"
        val testObserver = TestObserver<String>()

        homeViewModel.disaster.observeForever(testObserver)

        homeViewModel.setDisaster(expectedReturn)


        assertEquals(expectedReturn, testObserver.observedValue)

        homeViewModel.disaster.removeObserver(testObserver)
    }

    @Test
    fun `test setTimePeriod`() {
        val expectedReturn = 50
        val testObserver = TestObserver<Int>()

        homeViewModel.timePeriod.observeForever(testObserver)

        homeViewModel.setTimePeriod(expectedReturn)


        assertEquals(expectedReturn, testObserver.observedValue)

        homeViewModel.timePeriod.removeObserver(testObserver)
    }

    @Test
    fun `test getUrunDayaLiveData`() = runTest {
        val testData = Resource.Success(UrunDayaDummyData.dummyUrunDaya)

        val expectedReturn: Flow<Resource<UrunDaya>> = flowOf(testData)

        `when`(urunDayaRepository.getUrunDaya(any(), any(), any())).thenReturn(expectedReturn)

        urunDayaRepository.getUrunDaya("ID-JK").collect {
            assertEquals(testData, it)
        }

    }
}

class TestObserver<T> : Observer<T> {
    var observedValue: T? = null

    override fun onChanged(value: T) {
        observedValue = value
    }

}