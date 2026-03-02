package com.example.himalayanpunch.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.himalayanpunch.model.CafeOrder
import com.example.himalayanpunch.repository.TripRepoImpl
import com.google.firebase.auth.FirebaseAuth

class TripViewModel : ViewModel() {
    private val repo = TripRepoImpl()

    val trips = mutableStateListOf<CafeOrder>()
    var isLoading by mutableStateOf(false)

    fun loadTrips() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        isLoading = true
        repo.getUserOrders(uid) { _, _, list ->
            isLoading = false
            trips.clear()
            trips.addAll(list)
        }
    }

    fun updateTripStatus(tripId: String, status: String, onResult: (Boolean, String) -> Unit) =
        repo.updateOrderStatus(tripId, status, onResult)
}