package com.example.himalayanpunch.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.viewmodel.TripViewModel

// TripPlannerScreen — shows a user's active/planned orders (trip planner).

@Composable
fun TripPlannerScreen(
    padding: PaddingValues = PaddingValues(),
    tripViewModel: TripViewModel = viewModel()
) {
    val trips = tripViewModel.trips

    LaunchedEffect(Unit) { tripViewModel.loadTrips() }

    Column(
        modifier = Modifier.fillMaxSize().background(CafeColors.Espresso).padding(padding)
    ) {
        CafeScreenHeader(title = "Trip Planner", subtitle = "${trips.size} active orders")

        if (trips.isEmpty()) {
            CafeEmptyState(
                icon = "🗺️",
                title = "No trips planned",
                subtitle = "Start ordering to see your trip plan here"
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 22.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(trips) { trip ->
                    FlightScheduleCard(
                        order = trip,
                        onCancel = {
                            tripViewModel.updateTripStatus(trip.orderId, "Cancelled") { _, _ -> }
                        }
                    )
                }
            }
        }
    }
}