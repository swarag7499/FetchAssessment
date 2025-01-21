package com.example.fetchrewardassessment.model

data class UiStateModel(
    val groupedItems: List<Pair<Int, List<ItemModel>>> = emptyList()
)
