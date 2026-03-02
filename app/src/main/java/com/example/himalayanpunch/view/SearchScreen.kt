package com.example.himalayanpunch.view

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.himalayanpunch.ItemDetailActivity
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType
import com.example.himalayanpunch.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    padding: PaddingValues = PaddingValues(),
    searchViewModel: SearchViewModel = viewModel()
) {
    val ctx = LocalContext.current
    val results = searchViewModel.results

    Column(modifier = Modifier.fillMaxSize().background(CafeColors.Espresso).padding(padding)) {
        Column(
            modifier = Modifier.fillMaxWidth().background(CafeColors.Roast)
                .padding(start = 24.dp, end = 24.dp, top = 52.dp, bottom = 18.dp)
        ) {
            Text("Search", style = CafeType.headingLg.copy(color = CafeColors.TextPrimary))
            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = searchViewModel.query,
                onValueChange = { searchViewModel.query = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search coffee, food...", style = CafeType.bodyMd.copy(color = CafeColors.TextMuted)) },
                leadingIcon = { Text("🔍", style = TextStyle(fontSize = 16.sp)) },
                trailingIcon = {
                    if (searchViewModel.query.isNotEmpty()) {
                        IconButton(onClick = { searchViewModel.query = "" }) {
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
        }

        if (searchViewModel.query.isEmpty()) {
            CafeEmptyState("🔍", "Start Searching", "Type to search for coffee, tea, or food")
        } else if (results.isEmpty()) {
            CafeEmptyState("😕", "No Results", "No items match \"${searchViewModel.query}\"")
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