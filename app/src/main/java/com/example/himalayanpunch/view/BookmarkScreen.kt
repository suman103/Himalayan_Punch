package com.example.himalayanpunch.view

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.himalayanpunch.ItemDetailActivity
import com.example.himalayanpunch.model.FavouriteItem
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType
import com.example.himalayanpunch.utils.FavouritesManager
import com.example.himalayanpunch.utils.MockData

@Composable
fun BookmarkScreen(padding: PaddingValues = PaddingValues()) {
    val items = FavouritesManager.items
    val ctx = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CafeColors.Espresso)
            .padding(padding)
    ) {
        CafeScreenHeader(title = "Saved Items", subtitle = "${items.size} favourites")

        if (items.isEmpty()) {
            CafeEmptyState(
                icon = "❤️",
                title = "Nothing saved yet",
                subtitle = "Tap the heart on any menu item to save it here"
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 22.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items) { fav ->
                    FavouriteCard(
                        item = fav,
                        onClick = {
                            val menuItem = MockData.menuItems.find { it.itemId == fav.itemId }
                            if (menuItem != null) {
                                val intent = Intent(ctx, ItemDetailActivity::class.java)
                                intent.putExtra("itemId", menuItem.itemId)
                                ctx.startActivity(intent)
                            }
                        },
                        onRemove = {
                            val menuItem = MockData.menuItems.find { it.itemId == fav.itemId }
                            if (menuItem != null) FavouritesManager.toggle(menuItem)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FavouriteCard(item: FavouriteItem, onClick: () -> Unit, onRemove: () -> Unit) {
    val catColor = when (item.category) {
        "Hot Coffee", "Tea" -> CafeColors.Amber
        "Cold Coffee"       -> Color(0xFF5B8FA8)
        else                -> Color(0xFF8F6B3E)
    }

    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = CafeColors.Roast,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(80.dp).clip(RoundedCornerShape(14.dp))) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .align(Alignment.TopEnd)
                        .offset((-4).dp, 4.dp)
                        .background(
                            if (item.isVeg) CafeColors.Sage else CafeColors.Red,
                            CircleShape
                        )
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.category, style = CafeType.labelSm.copy(color = catColor))
                Text(
                    item.name,
                    style = CafeType.titleLg.copy(color = CafeColors.TextPrimary),
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(item.price, style = CafeType.titleLg.copy(color = CafeColors.Amber))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⭐", style = TextStyle(fontSize = 10.sp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            item.rating.toString(),
                            style = CafeType.labelSm.copy(color = CafeColors.TextMuted)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onRemove, modifier = Modifier.size(36.dp)) {
                Text("🗑️", style = TextStyle(fontSize = 15.sp))
            }
        }
    }
}