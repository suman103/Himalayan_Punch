package com.example.himalayanpunch.view.admin

import android.widget.Toast
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
import com.example.himalayanpunch.model.MenuItem
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType
import com.example.himalayanpunch.utils.MockData
import com.example.himalayanpunch.view.CafeField
import com.example.himalayanpunch.viewmodel.MenuViewModel

@Composable
fun AdminManagePlacesScreen(viewModel: MenuViewModel, padding: PaddingValues) {
    var showAddSheet by remember { mutableStateOf(false) }
    val ctx = LocalContext.current
    val items = viewModel.items.ifEmpty { MockData.menuItems }

    Column(modifier = Modifier.fillMaxSize().background(CafeColors.Espresso).padding(padding)) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(Brush.verticalGradient(listOf(CafeColors.Roast, CafeColors.Espresso)))
        ) {
            Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 52.dp, bottom = 18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Menu Items", style = CafeType.headingLg.copy(color = CafeColors.TextPrimary))
                        Text("${items.size} items on menu", style = CafeType.bodyMd.copy(color = CafeColors.TextSecondary))
                    }
                    Button(
                        onClick = { showAddSheet = true },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CafeColors.Amber),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                        elevation = ButtonDefaults.buttonElevation(0.dp)
                    ) { Text("+ Add", style = CafeType.label.copy(color = Color.White)) }
                }
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 22.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items) { item ->
                AdminMenuRow(
                    item = item,
                    onDelete = {
                        viewModel.deleteItem(item.itemId) { success, msg ->
                            Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
                            if (success) viewModel.loadItems()
                        }
                    }
                )
            }
        }
    }

    if (showAddSheet) {
        AdminAddItemSheet(
            onDismiss = { showAddSheet = false },
            onAdd = { item ->
                viewModel.addItem(item) { success, msg ->
                    Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
                    if (success) { viewModel.loadItems(); showAddSheet = false }
                }
            }
        )
    }
}

@Composable
fun AdminMenuRow(item: MenuItem, onDelete: () -> Unit) {
    val catColor = when (item.category) {
        "Hot Coffee", "Tea" -> CafeColors.Amber
        "Cold Coffee"       -> Color(0xFF5B8FA8)
        else                -> Color(0xFF8F6B3E)
    }

    Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = CafeColors.Roast) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(70.dp, 58.dp).clip(RoundedCornerShape(12.dp))) {
                AsyncImage(item.imageUrl, item.name, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                Box(
                    modifier = Modifier.size(8.dp).align(Alignment.TopStart).offset(4.dp, 4.dp)
                        .background(if (item.isVeg) CafeColors.Sage else CafeColors.Red, CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.category, style = CafeType.labelSm.copy(color = catColor))
                Text(item.name, style = CafeType.titleLg.copy(color = CafeColors.TextPrimary), maxLines = 1)
                Text(item.price, style = CafeType.bodyMd.copy(color = CafeColors.Amber))
            }
            Surface(
                onClick = onDelete,
                shape = RoundedCornerShape(8.dp),
                color = CafeColors.RedDim,
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("🗑", style = TextStyle(fontSize = 14.sp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAddItemSheet(onDismiss: () -> Unit, onAdd: (MenuItem) -> Unit) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Hot Coffee") }
    var subCategory by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var prepTime by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isVeg by remember { mutableStateOf(true) }

    val categories = listOf("Hot Coffee", "Cold Coffee", "Tea", "Food", "Specials")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = CafeColors.Roast,
        shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp),
        dragHandle = {
            Box(modifier = Modifier.padding(vertical = 12.dp).width(40.dp).height(4.dp)
                .clip(RoundedCornerShape(2.dp)).background(CafeColors.DividerLight))
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Add Menu Item", style = CafeType.headingMd.copy(color = CafeColors.TextPrimary))
                    IconButton(onClick = onDismiss) { Text("✕", style = CafeType.titleLg.copy(color = CafeColors.TextMuted)) }
                }
                HorizontalDivider(color = CafeColors.Divider)
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Category", style = CafeType.label.copy(color = CafeColors.TextSecondary))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(categories) { cat ->
                            val sel = category == cat
                            Surface(
                                onClick = { category = cat },
                                shape = RoundedCornerShape(10.dp),
                                color = if (sel) CafeColors.Amber else CafeColors.Mocha
                            ) {
                                Text(cat, style = CafeType.labelSm.copy(color = if (sel) Color.White else CafeColors.TextSecondary),
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp))
                            }
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Vegetarian", style = CafeType.label.copy(color = CafeColors.TextSecondary))
                    Switch(
                        checked = isVeg,
                        onCheckedChange = { isVeg = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = CafeColors.Sage,
                            uncheckedTrackColor = CafeColors.Mocha
                        )
                    )
                }
            }

            item { CafeField("Item Name", name, { name = it }, "e.g. Himalayan Latte", "☕") }
            item { CafeField("Sub-Category", subCategory, { subCategory = it }, "e.g. Latte, Frappe", "📋") }
            item { CafeField("Price", price, { price = it }, "e.g. Rs. 280", "💰") }
            item { CafeField("Calories", calories, { calories = it }, "e.g. 180 kcal", "🔥") }
            item { CafeField("Prep Time", prepTime, { prepTime = it }, "e.g. 5 min", "⏱") }
            item { CafeField("Image URL", imageUrl, { imageUrl = it }, "https://...", "🖼️") }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Description", style = CafeType.label.copy(color = CafeColors.TextSecondary))
                    OutlinedTextField(
                        value = description, onValueChange = { description = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Describe this item...", style = CafeType.bodyMd.copy(color = CafeColors.TextMuted)) },
                        shape = RoundedCornerShape(12.dp), maxLines = 4,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CafeColors.Amber, unfocusedBorderColor = CafeColors.DividerLight,
                            focusedContainerColor = CafeColors.Mocha, unfocusedContainerColor = CafeColors.Mocha,
                            focusedTextColor = CafeColors.TextPrimary, unfocusedTextColor = CafeColors.TextPrimary
                        )
                    )
                }
            }

            item {
                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            onAdd(MenuItem(
                                name = name, category = category, subCategory = subCategory,
                                price = price, priceValue = price.filter { it.isDigit() }.toLongOrNull() ?: 0L,
                                calories = calories, prepTime = prepTime, imageUrl = imageUrl,
                                description = description, isVeg = isVeg
                            ))
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CafeColors.Amber),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Text("ADD TO MENU", style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, letterSpacing = 1.5.sp))
                }
            }
        }
    }
}