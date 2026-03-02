package com.example.himalayanpunch.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.himalayanpunch.model.CafeOrder
import com.example.himalayanpunch.repository.BookmarkRepoImpl
import com.google.firebase.auth.FirebaseAuth

class BookingViewModel : ViewModel() {
    private val repo = BookmarkRepoImpl()

    val userOrders = mutableStateListOf<CafeOrder>()
    val allOrders  = mutableStateListOf<CafeOrder>()
    var isLoading by mutableStateOf(false)

    fun placeOrder(order: CafeOrder, onResult: (Boolean, String) -> Unit) =
        repo.createOrder(order, onResult)

    fun loadUserOrders() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        isLoading = true
        repo.getUserOrders(uid) { _, _, list ->
            isLoading = false
            userOrders.clear()
            userOrders.addAll(list)
        }
    }

    fun loadAllOrders() {
        isLoading = true
        repo.getAllOrders { _, _, list ->
            isLoading = false
            allOrders.clear()
            allOrders.addAll(list)
        }
    }

    fun updateStatus(orderId: String, status: String, onResult: (Boolean, String) -> Unit) =
        repo.updateOrderStatus(orderId, status, onResult)
}