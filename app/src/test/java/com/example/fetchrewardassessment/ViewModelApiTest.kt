package com.example.fetchrewardassessment

import app.cash.turbine.test
import com.example.fetchrewardassessment.viewmodel.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ViewModelTest {

    private lateinit var viewModel: ViewModel

    @Before
    fun setup() {
        viewModel = ViewModel()
    }

    @Test
    fun `test API response updates groupedItems`() = runTest {
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertTrue(initialState.groupedItems.isEmpty()) // Initial state should be empty

            val updatedState = awaitItem() // Wait for the next emitted state
            assertTrue(updatedState.groupedItems.isNotEmpty()) // Ensure groupedItems is updated
        }
    }

    @Test
    fun `test groupedItems are displayed in the list`() = runTest {
        viewModel.uiState.test {
            awaitItem() // Skip initial empty state

            val updatedState = awaitItem()
            assertTrue(updatedState.groupedItems.isNotEmpty()) // Ensure items are loaded

            // Validate data integrity
            val firstGroup = updatedState.groupedItems.first()
            assertTrue(firstGroup.first is Int) // Check group ID type
            assertTrue(firstGroup.second.isNotEmpty()) // Check items in group
            firstGroup.second.first().name?.let { assertTrue(it.isNotBlank()) } // Validate item name
        }
    }
}
