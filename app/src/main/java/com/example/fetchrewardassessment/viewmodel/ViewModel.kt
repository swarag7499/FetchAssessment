package com.example.fetchrewardassessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchrewardassessment.model.ItemModel
import com.example.fetchrewardassessment.model.UiStateModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class ViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiStateModel())
    val uiState: StateFlow<UiStateModel> = _uiState.asStateFlow()

    private val client = OkHttpClient()

    init {
        viewModelScope.launch {
            val items = fetchData()
            _uiState.update {
                it.copy(groupedItems = items)
            }
        }
    }

    private suspend fun fetchData(): List<Pair<Int, List<ItemModel>>> {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url("https://fetch-hiring.s3.amazonaws.com/hiring.json")
                    .build()

                val response = client.newCall(request).execute()
                val json = response.body?.string()
                json?.let {
                    parseData(it)
                } ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    private fun parseData(json: String): List<Pair<Int, List<ItemModel>>> {
        val type = object : TypeToken<List<ItemModel>>() {}.type
        val items: List<ItemModel> = Gson().fromJson(json, type)

        return items.filter { !it.name.isNullOrBlank() } // Filter invalid items
            .sortedWith(
                compareBy(
                    { it.listId }, // Sort by `listId`
                    { extractNumericValue(it.name) }, // Sort by number in `name`
                    { it.id } // Sort by numeric `id`
                )
            )
            .groupBy { it.listId } // Group by `listId`
            .toList()
    }

    // Function to extract the numeric value from the `name` field
    private fun extractNumericValue(name: String?): Int {
        return name?.replace("Item", "")?.trim()?.toIntOrNull() ?: Int.MAX_VALUE
    }

}