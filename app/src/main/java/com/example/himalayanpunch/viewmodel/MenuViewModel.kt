package com.example.himalayanpunch.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.himalayanpunch.model.MenuItem
import com.example.himalayanpunch.repository.BookingRepoImpl

class MenuViewModel : ViewModel() {
    private val repo = BookingRepoImpl()

    val items = mutableStateListOf<MenuItem>()
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    val hotItems   get() = items.filter { it.category == "Hot Coffee" || it.category == "Tea" }
    val coldItems  get() = items.filter { it.category == "Cold Coffee" }
    val foodItems  get() = items.filter { it.category == "Food" }
    val featured   get() = items.filter { it.isFeatured }

    fun loadItems() {
        isLoading = true
        repo.getAllItems { success, msg, list ->
            isLoading = false
            if (success) { items.clear(); items.addAll(list) }
            else errorMessage = msg
        }
    }

    fun addItem(item: MenuItem, onResult: (Boolean, String) -> Unit) =
        repo.addItem(item, onResult)

    fun updateItem(item: MenuItem, onResult: (Boolean, String) -> Unit) =
        repo.updateItem(item, onResult)

    fun deleteItem(itemId: String, onResult: (Boolean, String) -> Unit) =
        repo.deleteItem(itemId, onResult)
}