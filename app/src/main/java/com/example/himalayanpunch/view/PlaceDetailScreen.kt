package com.example.himalayanpunch.view

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
import com.example.himalayanpunch.model.CafeOrder
import com.example.himalayanpunch.model.MenuItem
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType
import com.example.himalayanpunch.utils.FavouritesManager
import com.example.himalayanpunch.viewmodel.BookingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailScreen(
    item: MenuItem,
    bookingViewModel: BookingViewModel,
    onBack: () -> Unit
) {
    var showOrderSheet by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var successOrderId by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isFav = FavouritesManager.isFavourite(item.itemId)

    val catColor = when (item.category) {
        "Hot Coffee", "Tea" -> CafeColors.Amber
        "Cold Coffee"       -> Color(0xFF5B8FA8)
        else                -> Color(0xFF8F6B3E)
    }

    if (showSuccess) {
        OrderSuccessScreen(
            orderId = successOrderId,
            itemName = item.name,
            onDone = onBack
        )
        return
    }

    Box(modifier = Modifier.fillMaxSize().background(CafeColors.Espresso)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Box(modifier = Modifier.fillMaxWidth().height(320.dp)) {
                    AsyncImage(
                        model = item.imageUrl,
                        contentDescription = item.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(modifier = Modifier.fillMaxSize().background(
                        Brush.verticalGradient(listOf(Color.Black.copy(0.2f), Color.Transparent, Color.Black.copy(0.75f)))
                    ))
                    Box(
                        modifier = Modifier.padding(top = 50.dp, start = 16.dp).size(40.dp)
                            .background(Color.Black.copy(0.45f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = onBack, modifier = Modifier.size(40.dp)) {
                            Text("←", style = TextStyle(fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold))
                        }
                    }
                    Box(
                        modifier = Modifier.padding(top = 50.dp, end = 16.dp).size(40.dp)
                            .align(Alignment.TopEnd)
                            .background(Color.Black.copy(0.45f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = { FavouritesManager.toggle(item) }, modifier = Modifier.size(40.dp)) {
                            Text(if (isFav) "❤️" else "🤍", style = TextStyle(fontSize = 18.sp))
                        }
                    }
                    Column(modifier = Modifier.align(Alignment.BottomStart).padding(18.dp)) {
                        Text(item.category, style = CafeType.label.copy(color = catColor))
                        Text(item.name, style = CafeType.headingLg.copy(color = Color.White))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("⭐ ${item.rating}", style = CafeType.bodyMd.copy(color = Color.White.copy(0.8f)))
                            Text("⏱ ${item.prepTime}", style = CafeType.bodyMd.copy(color = Color.White.copy(0.8f)))
                            Text("🔥 ${item.calories}", style = CafeType.bodyMd.copy(color = Color.White.copy(0.8f)))
                        }
                    }
                    Surface(
                        modifier = Modifier.align(Alignment.BottomEnd).padding(18.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = CafeColors.Amber
                    ) {
                        Text(
                            item.price,
                            style = CafeType.titleLg.copy(color = Color.White, fontWeight = FontWeight.ExtraBold),
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            if (item.tags.isNotEmpty()) {
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(item.tags) { tag -> MenuTag(tag) }
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {
                    Text("About This Item", style = CafeType.headingMd.copy(color = CafeColors.TextPrimary))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(item.description, style = CafeType.bodyLg.copy(color = CafeColors.TextSecondary))
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text("Available Sizes", style = CafeType.headingMd.copy(color = CafeColors.TextPrimary))
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        item.sizes.forEach { size ->
                            Surface(shape = RoundedCornerShape(10.dp), color = CafeColors.Roast, tonalElevation = 0.dp) {
                                Text(size, style = CafeType.titleMd.copy(color = CafeColors.TextPrimary),
                                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp))
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(120.dp)) }
        }

        Surface(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
            color = CafeColors.Roast, tonalElevation = 0.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { FavouritesManager.toggle(item) },
                    modifier = Modifier.size(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = if (isFav) CafeColors.Red else CafeColors.Amber
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) { Text(if (isFav) "❤️" else "🤍", style = TextStyle(fontSize = 20.sp)) }
                Button(
                    onClick = { showOrderSheet = true },
                    modifier = Modifier.weight(1f).height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CafeColors.Amber),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Text("ORDER NOW", style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, letterSpacing = 1.5.sp))
                }
            }
        }
    }

    if (showOrderSheet) {
        ModalBottomSheet(
            onDismissRequest = { showOrderSheet = false },
            sheetState = sheetState,
            containerColor = CafeColors.Roast,
            shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp),
            dragHandle = {
                Box(modifier = Modifier.padding(vertical = 12.dp).width(40.dp).height(4.dp)
                    .clip(RoundedCornerShape(2.dp)).background(CafeColors.DividerLight))
            }
        ) {
            OrderSheet(
                item = item,
                bookingViewModel = bookingViewModel,
                onDismiss = { showOrderSheet = false },
                onSuccess = { id -> showOrderSheet = false; successOrderId = id; showSuccess = true }
            )
        }
    }
}

@Composable
fun OrderSuccessScreen(orderId: String, itemName: String, onDone: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Brush.verticalGradient(listOf(CafeColors.Espresso, CafeColors.Roast))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text("☕", style = TextStyle(fontSize = 64.sp))
            Text("Order Placed!", style = CafeType.displayLg.copy(color = CafeColors.TextPrimary))
            Text(
                "Your $itemName is being prepared. We'll have it ready soon!",
                style = CafeType.bodyLg.copy(color = CafeColors.TextSecondary),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp), color = CafeColors.Roast) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Order Details", style = CafeType.titleLg.copy(color = CafeColors.Amber))
                    HorizontalDivider(color = CafeColors.Divider)
                    if (orderId.isNotEmpty()) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Order ID", style = CafeType.bodyMd.copy(color = CafeColors.TextSecondary))
                            Text(orderId.take(16), style = CafeType.titleMd.copy(color = CafeColors.TextPrimary))
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Status", style = CafeType.bodyMd.copy(color = CafeColors.TextSecondary))
                        Surface(shape = RoundedCornerShape(6.dp), color = CafeColors.AmberDim) {
                            Text("⏳ Pending", style = CafeType.label.copy(color = CafeColors.Amber),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                        }
                    }
                }
            }
            Button(
                onClick = onDone,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CafeColors.Amber),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Text("BACK TO MENU", style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, letterSpacing = 1.5.sp))
            }
        }
    }
}

@Composable
fun OrderSheet(
    item: MenuItem,
    bookingViewModel: BookingViewModel,
    onDismiss: () -> Unit,
    onSuccess: (String) -> Unit
) {
    var customerName by remember { mutableStateOf("") }
    var customerPhone by remember { mutableStateOf("") }
    var deliveryAddress by remember { mutableStateOf("") }
    var selectedSize by remember { mutableStateOf(item.sizes.getOrElse(1) { item.sizes.first() }) }
    var selectedCustom by remember { mutableStateOf("") }
    var selectedOrderType by remember { mutableStateOf("Dine In") }
    var quantity by remember { mutableStateOf(1) }
    var specialNote by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val ctx = LocalContext.current
    val orderTypes = listOf("Dine In", "Takeaway", "Delivery")

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp).padding(bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("Place Order", style = CafeType.headingMd.copy(color = CafeColors.TextPrimary))
                Text(item.name, style = CafeType.bodyMd.copy(color = CafeColors.Amber), maxLines = 1)
            }
            IconButton(onClick = onDismiss) { Text("✕", style = CafeType.titleLg.copy(color = CafeColors.TextMuted)) }
        }

        HorizontalDivider(color = CafeColors.Divider)

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Size", style = CafeType.label.copy(color = CafeColors.TextSecondary))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item.sizes.forEach { size ->
                    val sel = selectedSize == size
                    Surface(onClick = { selectedSize = size }, shape = RoundedCornerShape(10.dp), color = if (sel) CafeColors.Amber else CafeColors.Mocha) {
                        Text(size, style = CafeType.label.copy(color = if (sel) Color.White else CafeColors.TextSecondary),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                    }
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Order Type", style = CafeType.label.copy(color = CafeColors.TextSecondary))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                orderTypes.forEach { type ->
                    val sel = selectedOrderType == type
                    Surface(onClick = { selectedOrderType = type }, shape = RoundedCornerShape(10.dp), color = if (sel) CafeColors.Amber else CafeColors.Mocha) {
                        Text(type, style = CafeType.labelSm.copy(color = if (sel) Color.White else CafeColors.TextSecondary),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp))
                    }
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Quantity", style = CafeType.label.copy(color = CafeColors.TextSecondary))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Surface(onClick = { if (quantity > 1) quantity-- }, shape = CircleShape, color = CafeColors.Mocha, modifier = Modifier.size(34.dp)) {
                    Box(contentAlignment = Alignment.Center) { Text("−", style = CafeType.titleLg.copy(color = CafeColors.Amber)) }
                }
                Text("$quantity", style = CafeType.headingMd.copy(color = CafeColors.TextPrimary))
                Surface(onClick = { quantity++ }, shape = CircleShape, color = CafeColors.Amber, modifier = Modifier.size(34.dp)) {
                    Box(contentAlignment = Alignment.Center) { Text("+", style = CafeType.titleLg.copy(color = Color.White)) }
                }
            }
        }

        CafeField("Your Name", customerName, { customerName = it }, "Full name", "👤")
        CafeField("Phone", customerPhone, { customerPhone = it }, "+977-XXXXXXXXXX", "📞")
        if (selectedOrderType == "Delivery") {
            CafeField("Delivery Address", deliveryAddress, { deliveryAddress = it }, "Your address", "📍")
        }

        Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), color = CafeColors.AmberDim) {
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Total (${quantity}x)", style = CafeType.titleMd.copy(color = CafeColors.TextSecondary))
                Text("Rs. ${item.priceValue * quantity}", style = CafeType.price.copy(color = CafeColors.Amber))
            }
        }

        Button(
            onClick = {
                if (customerName.isNotBlank() && customerPhone.isNotBlank()) {
                    isLoading = true
                    bookingViewModel.placeOrder(CafeOrder(
                        itemId = item.itemId, itemName = item.name, itemCategory = item.category,
                        itemImageUrl = item.imageUrl, itemPrice = item.price,
                        quantity = quantity, size = selectedSize, customization = selectedCustom,
                        customerName = customerName, customerPhone = customerPhone,
                        deliveryAddress = deliveryAddress, orderType = selectedOrderType,
                        specialNote = specialNote, totalPrice = "Rs. ${item.priceValue * quantity}"
                    )) { success, msg ->
                        isLoading = false
                        if (success) onSuccess(msg.substringAfter("ID: ").trim())
                        else Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CafeColors.Amber),
            enabled = !isLoading,
            elevation = ButtonDefaults.buttonElevation(0.dp)
        ) {
            if (isLoading) CircularProgressIndicator(Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
            else Text("PLACE ORDER", style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, letterSpacing = 1.5.sp))
        }
    }
}