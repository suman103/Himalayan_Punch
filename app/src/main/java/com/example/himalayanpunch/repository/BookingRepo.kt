package com.example.himalayanpunch.repository

import com.example.himalayanpunch.model.MenuItem

// ── Menu Item Repository ──────────────────────────────────────────────────────

interface BookingRepo {
    fun getAllItems(onResult: (Boolean, String, List<MenuItem>) -> Unit)
    fun addItem(item: MenuItem, onResult: (Boolean, String) -> Unit)
    fun updateItem(item: MenuItem, onResult: (Boolean, String) -> Unit)
    fun deleteItem(itemId: String, onResult: (Boolean, String) -> Unit)
}