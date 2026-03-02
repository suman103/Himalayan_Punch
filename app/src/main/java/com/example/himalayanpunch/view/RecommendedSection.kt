package com.example.himalayanpunch.view

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.himalayanpunch.ItemDetailActivity
import com.example.himalayanpunch.model.MenuItem
import com.example.himalayanpunch.utils.MockData

// RecommendedSection — displays featured/recommended menu items as a horizontal card row.

@Composable
fun RecommendedSection(
    padding: PaddingValues = PaddingValues(),
    items: List<MenuItem> = MockData.featured
) {
    val ctx = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth().padding(padding)) {
        CafeSectionHeader(title = "Today's Picks", badge = "${items.size} items")
        Spacer(modifier = Modifier.height(14.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(items) { item ->
                FeaturedMenuCard(item = item, onClick = {
                    ctx.startActivity(Intent(ctx, ItemDetailActivity::class.java).apply {
                        putExtra("itemId", item.itemId)
                    })
                })
            }
        }
    }
}