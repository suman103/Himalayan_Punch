package com.example.himalayanpunch.view

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.himalayanpunch.ItemDetailActivity
import com.example.himalayanpunch.model.MenuItem
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType

// ResultsScreen — displays a filtered list of menu items (search/filter results).

@Composable
fun ResultsScreen(
    padding: PaddingValues = PaddingValues(),
    results: List<MenuItem>,
    title: String = "Results"
) {
    val ctx = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().background(CafeColors.Espresso).padding(padding)
    ) {
        CafeScreenHeader(title = title, subtitle = "${results.size} items found")

        if (results.isEmpty()) {
            CafeEmptyState("🔍", "No results", "Try adjusting your search or filters")
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(results) { item ->
                    MenuListCard(
                        item = item,
                        onClick = {
                            ctx.startActivity(Intent(ctx, ItemDetailActivity::class.java).apply {
                                putExtra("itemId", item.itemId)
                            })
                        }
                    )
                }
            }
        }
    }
}