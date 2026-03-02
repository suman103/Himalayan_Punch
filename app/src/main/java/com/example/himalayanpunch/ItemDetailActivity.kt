package com.example.himalayanpunch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType
import com.example.himalayanpunch.utils.MockData
import com.example.himalayanpunch.view.PlaceDetailScreen
import com.example.himalayanpunch.viewmodel.BookingViewModel

class ItemDetailActivity : ComponentActivity() {
    private val bookingViewModel: BookingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val itemId = intent.getStringExtra("itemId") ?: ""
        val item = MockData.menuItems.find { it.itemId == itemId }

        setContent {
            if (item != null) {
                PlaceDetailScreen(
                    item = item,
                    bookingViewModel = bookingViewModel,
                    onBack = { finish() }
                )
            } else {
                Box(Modifier.fillMaxSize().background(CafeColors.Espresso), Alignment.Center) {
                    Text("Item not found", style = CafeType.headingMd.copy(color = CafeColors.TextPrimary))
                }
            }
        }
    }
}