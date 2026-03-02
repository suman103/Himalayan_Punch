package com.example.himalayanpunch.viewmodel

import androidx.lifecycle.ViewModel
import com.example.himalayanpunch.model.FavouriteItem
import com.example.himalayanpunch.model.MenuItem
import com.example.himalayanpunch.utils.FavouritesManager

class BookmarkViewModel : ViewModel() {
    val bookmarks get() = FavouritesManager.items

    fun isSaved(itemId: String) = FavouritesManager.isFavourite(itemId)

    fun toggle(item: MenuItem) = FavouritesManager.toggle(item)

    fun remove(item: FavouriteItem) {
        FavouritesManager.items.remove(item)
    }
}