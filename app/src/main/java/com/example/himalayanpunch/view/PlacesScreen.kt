package com.example.himalayanpunch.view

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.himalayanpunch.ItemDetailActivity
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType
import com.example.himalayanpunch.utils.MockData

@Composable
fun PlacesScreen(padding: PaddingValues = PaddingValues()) {
    var query by remember { mutableStateOf("") }
    var selectedCat by remember { mutableStateOf("All") }
    var selectedSort by remember { mutableStateOf("Default") }
    val ctx = LocalContext.current

    val categories = listOf("All", "Hot Coffee", "Tea", "Cold Coffee", "Food")
    val sorts = listOf("Default", "Price ↑", "Price ↓", "Rating", "Prep Time")

    val filtered = remember(query, selectedCat, selectedSort) {
        var list = MockData.menuItems.toList()
        if (selectedCat != "All") {
            list = list.filter { it.category == selectedCat }
        }
        if (query.isNotEmpty()) {
            list = list.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true) ||
                        it.subCategory.contains(query, ignoreCase = true) ||
                        it.tags.any { t -> t.contains(query, ignoreCase = true) }
            }
        }
        when (selectedSort) {
            "Price ↑"   -> list.sortedBy { it.priceValue }
            "Price ↓"   -> list.sortedByDescending { it.priceValue }
            "Rating"    -> list.sortedByDescending { it.rating }
            "Prep Time" -> list.sortedBy { it.prepTime.filter { c -> c.isDigit() }.toIntOrNull() ?: 99 }
            else        -> list
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(CafeColors.Espresso).padding(padding)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(CafeColors.Roast)
                .padding(start = 24.dp, end = 24.dp, top = 52.dp, bottom = 18.dp)
        ) {
            Text("Our Menu", style = CafeType.headingLg.copy(color = CafeColors.TextPrimary))
            Text("${filtered.size} items available", style = CafeType.bodyMd.copy(color = CafeColors.TextSecondary))

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search menu items...", style = CafeType.bodyMd.copy(color = CafeColors.TextMuted)) },
                leadingIcon = { Text("🔍", style = TextStyle(fontSize = 16.sp)) },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Text("✕", style = CafeType.label.copy(color = CafeColors.TextMuted))
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CafeColors.Amber, unfocusedBorderColor = CafeColors.DividerLight,
                    focusedContainerColor = CafeColors.Mocha, unfocusedContainerColor = CafeColors.Mocha,
                    focusedTextColor = CafeColors.TextPrimary, unfocusedTextColor = CafeColors.TextPrimary
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val catIcons = mapOf("All" to "🍽", "Hot Coffee" to "☕", "Tea" to "🍵", "Cold Coffee" to "🧊", "Food" to "🥪")
                categories.forEach { cat ->
                    val selected = selectedCat == cat
                    Surface(
                        onClick = { selectedCat = cat },
                        shape = RoundedCornerShape(10.dp),
                        color = if (selected) CafeColors.Amber else CafeColors.Mocha
                    ) {
                        Text(
                            text = "${catIcons[cat]} $cat",
                            style = CafeType.labelSm.copy(color = if (selected) Color.White else CafeColors.TextSecondary),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                var showSort by remember { mutableStateOf(false) }
                Box {
                    Surface(onClick = { showSort = true }, shape = RoundedCornerShape(10.dp), color = CafeColors.Mocha) {
                        Text("↕ $selectedSort", style = CafeType.labelSm.copy(color = CafeColors.TextSecondary),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp))
                    }
                    DropdownMenu(expanded = showSort, onDismissRequest = { showSort = false }, modifier = Modifier.background(CafeColors.Mocha)) {
                        sorts.forEach { s ->
                            DropdownMenuItem(
                                text = { Text(s, style = CafeType.bodyMd.copy(color = CafeColors.TextPrimary)) },
                                onClick = { selectedSort = s; showSort = false }
                            )
                        }
                    }
                }
            }
        }

        if (filtered.isEmpty()) {
            CafeEmptyState(icon = "☕", title = "Nothing found", subtitle = "Try a different search or category")
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filtered) { item ->
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