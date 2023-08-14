package com.imannuel.petabencana.ui.saved.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.imannuel.petabencana.data.repository.UrunDayaRepository
import com.imannuel.petabencana.ui.home.TestObserver
import com.imannuel.petabencana.ui.saved.PropertiesDummyData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SavedDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockUrunDayaRepository: UrunDayaRepository

    private lateinit var viewModel: SavedDetailViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = SavedDetailViewModel(mockUrunDayaRepository)
    }

    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }


    @Test
    fun `test saveUrunDaya sets isSaved to true`() = runTest {
        val urunDaya = PropertiesDummyData.sampleProperties

        viewModel.saveUrunDaya(urunDaya)

        verify(mockUrunDayaRepository).saveUrunDaya(urunDaya)
    }

    @Test
    fun `test isUrunDayaExist run correctly`() {
        val id = "id"

        val expectedReturn = MutableLiveData<Int>()
        expectedReturn.value = 1

        val testObserver = TestObserver<Int>()

        `when`(mockUrunDayaRepository.isUrunDayaExist(id)).thenReturn(expectedReturn)

        viewModel.isUrunDayaExist(id).observeForever(testObserver)

        verify(mockUrunDayaRepository).isUrunDayaExist(id)

        assertEquals(1, viewModel.isUrunDayaExist(id).value)

        viewModel.isUrunDayaExist(id).removeObserver(testObserver)
    }

    @Test
    fun `test deleteUrunDaya run correctly`() = runTest {
        val id = "id"

        viewModel.deleteUrunDaya(id)

        verify(mockUrunDayaRepository).deleteUrunDaya(id)
    }
}






