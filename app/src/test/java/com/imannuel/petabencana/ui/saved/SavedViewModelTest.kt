package com.imannuel.petabencana.ui.saved

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.imannuel.petabencana.data.model.Properties
import com.imannuel.petabencana.data.repository.UrunDayaRepository
import com.imannuel.petabencana.ui.saved.PropertiesDummyData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SavedViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockUrunDayaRepository: UrunDayaRepository

    private lateinit var viewModel: SavedViewModel

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = SavedViewModel(mockUrunDayaRepository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `test getSavedUrunDaya returns expected data`() = runTest {
        val dummyData = listOf<Properties>(
            PropertiesDummyData.sampleProperties
        )

        val expectedReturn = MutableLiveData<List<Properties>>()
        expectedReturn.value = dummyData

        `when`(mockUrunDayaRepository.getAllSavedUrunDaya()).thenReturn(expectedReturn)

        val savedUrunDaya = viewModel.getSavedUrunDaya()

        verify(mockUrunDayaRepository).getAllSavedUrunDaya()

        assertEquals(dummyData, savedUrunDaya.value)
    }

    @Test
    fun `test deleteSavedUrunDaya run correctly`() = runTest {
        val id = "id"

        viewModel.deleteSavedUrunDaya(id)

        verify(mockUrunDayaRepository).deleteUrunDaya(id)
    }
}