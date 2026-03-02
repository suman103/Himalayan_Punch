package com.example.himalayanpunch.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.himalayanpunch.model.MenuItem
import com.example.himalayanpunch.repository.PlaceRepoImpl
import com.example.himalayanpunch.utils.MockData

class PlaceViewModel : ViewModel() {
    private val repo = PlaceRepoImpl()

    val places = mutableStateListOf<MenuItem>()
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    var searchQuery by mutableStateOf("")
    var selectedCategory by mutableStateOf("All")

    val filteredPlaces get() = MockData.menuItems.let { all ->
        var list = if (selectedCategory == "All") all else all.filter { it.category == selectedCategory }
        if (searchQuery.isNotEmpty()) {
            list = list.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.category.contains(searchQuery, ignoreCase = true)
            }
        }
        list
    }

    fun loadPlaces() {
        isLoading = true
        repo.getAllItems { success, msg, list ->
            isLoading = false
            if (success) { places.clear(); places.addAll(list) }
            else errorMessage = msg
        }
    }
}