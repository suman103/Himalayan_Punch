package com.example.himalayanpunch.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.himalayanpunch.model.MenuItem
import com.example.himalayanpunch.utils.MockData

class SearchViewModel : ViewModel() {
    var query by mutableStateOf("")
    var selectedCategory by mutableStateOf("All")
    var selectedSort by mutableStateOf("Default")

    val results: List<MenuItem> get() {
        var list = MockData.menuItems.toList()
        if (selectedCategory != "All") {
            list = list.filter { it.category == selectedCategory }
        }
        if (query.isNotEmpty()) {
            list = list.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true) ||
                        it.subCategory.contains(query, ignoreCase = true) ||
                        it.tags.any { t -> t.contains(query, ignoreCase = true) }
            }
        }
        return when (selectedSort) {
            "Price ↑"   -> list.sortedBy { it.priceValue }
            "Price ↓"   -> list.sortedByDescending { it.priceValue }
            "Rating"    -> list.sortedByDescending { it.rating }
            "Prep Time" -> list.sortedBy { it.prepTime.filter { c -> c.isDigit() }.toIntOrNull() ?: 99 }
            else        -> list
        }
    }
}