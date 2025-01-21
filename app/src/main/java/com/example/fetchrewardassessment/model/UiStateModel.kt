package com.example.fetchrewardassessment.model

// Represents the state of the UI, specifically the grouping of items by their listId
data class UiStateModel(
    val groupedItems: List<Pair<Int, List<ItemModel>>> = emptyList()
)
