package com.example.fetchrewardassessment.uielement

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.fetchrewardassessment.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
//created to render the ui
fun GroupedItemScreen(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // A scrollable column that lazily loads items as needed.
    LazyColumn(modifier = modifier) {

        uiState.groupedItems.forEach { (listId, items) ->

            // Adds an item to the LazyColumn for each group.
            item {
                // Custom composable to display an expandable section for the group.

                RowExpandableSection(title = "List ID: $listId", items = items)
            }
        }
    }
}