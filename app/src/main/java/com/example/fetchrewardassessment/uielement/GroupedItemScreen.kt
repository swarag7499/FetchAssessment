package com.example.fetchrewardassessment.uielement

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.fetchrewardassessment.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun FetchHiringScreen(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(modifier = modifier) {

        uiState.groupedItems.forEach { (listId, items) ->

            item {
                ExpandableSection(title = "List ID: $listId", items = items)
            }
        }
    }
}