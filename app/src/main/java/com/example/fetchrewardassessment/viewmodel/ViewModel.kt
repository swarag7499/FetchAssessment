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

    // OkHttp client instance for performing network operations.
    private val client = OkHttpClient()

    init {
        // it Fetch data when the ViewModel is created.
        viewModelScope.launch {
            val items = fetchData()
            _uiState.update {
                it.copy(groupedItems = items)
            }
        }
    }

    // this will Fetches data from a remote JSON source.
    private suspend fun fetchData(): List<Pair<Int, List<ItemModel>>> {
        return withContext(Dispatchers.IO) {
            try {
                // Build and execute the HTTP request.
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

    // this will Parses JSON data into grouped and sorted items.
    private fun parseData(json: String): List<Pair<Int, List<ItemModel>>> {
        val type = object : TypeToken<List<ItemModel>>() {}.type
        // Parse the JSON string into a list of ItemModel objects.
        val items: List<ItemModel> = Gson().fromJson(json, type)

        return items.filter { !it.name.isNullOrBlank() }
            .sortedWith(
                compareBy(
                    { it.listId },
                    { extractNumericValue(it.name) },
                    { it.id }
                )
            )
            .groupBy { it.listId }
            .toList()
    }

    // this will Extracts numeric values from the item name for sorting.
    private fun extractNumericValue(name: String?): Int {
        return name?.replace("Item", "")?.trim()?.toIntOrNull() ?: Int.MAX_VALUE
    }

}