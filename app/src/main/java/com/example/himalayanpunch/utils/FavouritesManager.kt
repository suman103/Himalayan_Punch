package com.example.himalayanpunch.utils

import androidx.compose.runtime.mutableStateListOf
import com.example.himalayanpunch.model.FavouriteItem
import com.example.himalayanpunch.model.MenuItem

// ── Favourites Manager ────────────────────────────────────────────────────────

object FavouritesManager {
    val items = mutableStateListOf<FavouriteItem>()

    fun isFavourite(itemId: String) = items.any { it.itemId == itemId }

    fun toggle(item: MenuItem) {
        val existing = items.find { it.itemId == item.itemId }
        if (existing != null) {
            items.remove(existing)
        } else {
            items.add(
                FavouriteItem(
                    itemId    = item.itemId,
                    name      = item.name,
                    category  = item.category,
                    price     = item.price,
                    imageUrl  = item.imageUrl,
                    rating    = item.rating,
                    isVeg     = item.isVeg,
                    tags      = item.tags
                )
            )
        }
    }
}