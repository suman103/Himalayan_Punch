package com.example.himalayanpunch.view

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.himalayanpunch.ItemDetailActivity
import com.example.himalayanpunch.model.MenuItem
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType
import com.example.himalayanpunch.utils.FavouritesManager
import com.example.himalayanpunch.utils.MockData

@Composable
fun DashboardScreen(padding: PaddingValues = PaddingValues(), userName: String = "Guest") {
    val ctx      = LocalContext.current
    val featured = MockData.featured
    val allItems = MockData.menuItems

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CafeColors.Espresso)
            .padding(padding), // ✅ consumes scaffold insets — bottom nav is never blocked
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {

        // ── Hero header ──────────────────────────────────────────────────────
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(listOf(CafeColors.Roast, CafeColors.Espresso))
                    )
            ) {
                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 60.dp, y = (-40).dp)
                        .background(
                            Brush.radialGradient(
                                listOf(CafeColors.Amber.copy(0.1f), Color.Transparent)
                            ),
                            CircleShape
                        )
                )
                Column(
                    modifier = Modifier.padding(
                        start  = 24.dp,
                        end    = 24.dp,
                        top    = 56.dp,
                        bottom = 28.dp
                    )
                ) {
                    Text(
                        text  = "Good morning, ${userName.substringBefore(" ")} ☕",
                        style = CafeType.label.copy(color = CafeColors.Amber)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text  = "What would you\nlike today?",
                        style = CafeType.displayLg.copy(color = CafeColors.TextPrimary)
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Surface(
                        onClick        = {},
                        modifier       = Modifier.fillMaxWidth(),
                        shape          = RoundedCornerShape(16.dp),
                        color          = CafeColors.Mocha,
                        tonalElevation = 0.dp
                    ) {
                        Row(
                            modifier          = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🔍", style = TextStyle(fontSize = 16.sp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                "Search coffee, tea, food...",
                                style = CafeType.bodyMd.copy(color = CafeColors.TextMuted)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Box(
                                modifier         = Modifier
                                    .size(32.dp)
                                    .background(CafeColors.Amber, RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("✦", style = TextStyle(fontSize = 14.sp, color = Color.White))
                            }
                        }
                    }
                }
            }
        }

        // ── Category chips ───────────────────────────────────────────────────
        item {
            Spacer(modifier = Modifier.height(22.dp))
            LazyRow(
                contentPadding        = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val chips = listOf(
                    Triple("☕", "Hot Coffee", CafeColors.Amber),
                    Triple("🧊", "Cold Drinks", Color(0xFF5B8FA8)),
                    Triple("🍵", "Tea",         CafeColors.Sage),
                    Triple("🥪", "Food",        Color(0xFF8F6B3E))
                )
                items(chips) { (icon, label, color) ->
                    MenuCategoryChip(icon = icon, label = label, color = color)
                }
            }
        }

        // ── Today's Picks ────────────────────────────────────────────────────
        item {
            Spacer(modifier = Modifier.height(28.dp))
            CafeSectionHeader(title = "Today's Picks", badge = "${featured.size} items")
            Spacer(modifier = Modifier.height(14.dp))
            LazyRow(
                contentPadding        = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(featured) { item ->
                    FeaturedMenuCard(
                        item    = item,
                        onClick = {
                            ctx.startActivity(
                                Intent(ctx, ItemDetailActivity::class.java)
                                    .apply { putExtra("itemId", item.itemId) }
                            )
                        }
                    )
                }
            }
        }

        // ── Full menu header ─────────────────────────────────────────────────
        item {
            Spacer(modifier = Modifier.height(28.dp))
            CafeSectionHeader(title = "Full Menu", badge = "${allItems.size} items")
            Spacer(modifier = Modifier.height(14.dp))
        }

        // ── Menu list ────────────────────────────────────────────────────────
        items(allItems) { item ->
            Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 5.dp)) {
                MenuListCard(
                    item    = item,
                    onClick = {
                        ctx.startActivity(
                            Intent(ctx, ItemDetailActivity::class.java)
                                .apply { putExtra("itemId", item.itemId) }
                        )
                    }
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  MenuCategoryChip
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun MenuCategoryChip(icon: String, label: String, color: Color) {
    Surface(
        shape          = RoundedCornerShape(14.dp),
        color          = color.copy(alpha = 0.13f),
        tonalElevation = 0.dp
    ) {
        Row(
            modifier          = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(icon, style = TextStyle(fontSize = 16.sp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(label, style = CafeType.label.copy(color = color))
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  FeaturedMenuCard
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun FeaturedMenuCard(item: MenuItem, onClick: () -> Unit) {
    // ✅ Read directly from mutableStateListOf — Compose recomposes
    //    this card automatically whenever the favourites list changes.
    val isFav = FavouritesManager.items.any { it.itemId == item.itemId }

    val catColor = when (item.category) {
        "Hot Coffee", "Tea" -> CafeColors.Amber
        "Cold Coffee"       -> Color(0xFF5B8FA8)
        else                -> Color(0xFF8F6B3E)
    }

    Surface(
        onClick        = onClick,
        modifier       = Modifier.width(200.dp),
        shape          = RoundedCornerShape(20.dp),
        color          = CafeColors.Roast,
        tonalElevation = 0.dp
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(140.dp)) {
                AsyncImage(
                    model              = item.imageUrl,
                    contentDescription = item.name,
                    contentScale       = ContentScale.Crop,
                    modifier           = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(0.35f)),
                                startY = 60f
                            )
                        )
                )
                // Category badge
                Surface(
                    modifier = Modifier.align(Alignment.TopStart).padding(8.dp),
                    shape    = RoundedCornerShape(8.dp),
                    color    = catColor.copy(0.9f)
                ) {
                    Text(
                        item.category,
                        style    = CafeType.labelSm.copy(
                            color      = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                // Favourite toggle
                Surface(
                    onClick  = { FavouritesManager.toggle(item) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(30.dp),
                    shape = CircleShape,
                    color = Color.Black.copy(0.4f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            if (isFav) "❤️" else "🤍",
                            style = TextStyle(fontSize = 12.sp)
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                if (item.tags.isNotEmpty()) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        items(item.tags.take(2)) { tag -> MenuTag(tag) }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }
                Text(
                    item.name,
                    style      = CafeType.titleLg.copy(color = CafeColors.TextPrimary),
                    maxLines   = 2,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text(
                        item.price,
                        style = CafeType.price.copy(color = CafeColors.Amber, fontSize = 15.sp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⏱", style = TextStyle(fontSize = 10.sp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            item.prepTime,
                            style = CafeType.labelSm.copy(color = CafeColors.TextMuted)
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  MenuListCard
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun MenuListCard(item: MenuItem, onClick: () -> Unit) {
    // ✅ Same fix — read directly from mutableStateListOf
    val isFav = FavouritesManager.items.any { it.itemId == item.itemId }

    val catColor = when (item.category) {
        "Hot Coffee", "Tea" -> CafeColors.Amber
        "Cold Coffee"       -> Color(0xFF5B8FA8)
        else                -> Color(0xFF8F6B3E)
    }

    Surface(
        onClick        = onClick,
        modifier       = Modifier.fillMaxWidth(),
        shape          = RoundedCornerShape(18.dp),
        color          = CafeColors.Roast,
        tonalElevation = 0.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            Box(modifier = Modifier.size(90.dp).clip(RoundedCornerShape(14.dp))) {
                AsyncImage(
                    model              = item.imageUrl,
                    contentDescription = item.name,
                    contentScale       = ContentScale.Crop,
                    modifier           = Modifier.fillMaxSize()
                )
                // Veg / non-veg dot
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .align(Alignment.TopEnd)
                        .offset((-5).dp, 5.dp)
                        .background(
                            if (item.isVeg) CafeColors.Sage else CafeColors.Red,
                            CircleShape
                        )
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            item.category,
                            style = CafeType.labelSm.copy(color = catColor)
                        )
                        Text(
                            item.name,
                            style      = CafeType.titleLg.copy(color = CafeColors.TextPrimary),
                            maxLines   = 2,
                            lineHeight = 18.sp
                        )
                    }
                    // ✅ Favourite toggle — re-renders because isFav tracks mutableStateListOf
                    IconButton(
                        onClick  = { FavouritesManager.toggle(item) },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Text(
                            if (isFav) "❤️" else "🤍",
                            style = TextStyle(fontSize = 13.sp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    MenuTag(item.calories)
                    MenuTag(item.prepTime)
                    if (item.tags.isNotEmpty()) MenuTag(item.tags.first())
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text(
                        item.price,
                        style = CafeType.price.copy(color = CafeColors.Amber, fontSize = 15.sp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⭐", style = TextStyle(fontSize = 10.sp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            item.rating.toString(),
                            style = CafeType.labelSm.copy(color = CafeColors.TextSecondary)
                        )
                    }
                }
            }
        }
    }
}