package com.example.himalayanpunch.repository

import com.example.himalayanpunch.model.CafeOrder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BookmarkRepoImpl : BookmarkRepo {
    private val db = Firebase.database.reference.child("orders")
    private val auth = FirebaseAuth.getInstance()

    override fun createOrder(order: CafeOrder, onResult: (Boolean, String) -> Unit) {
        val id = db.push().key ?: return onResult(false, "Failed to generate ID")
        val uid   = auth.currentUser?.uid ?: ""
        val email = auth.currentUser?.email ?: ""
        val name  = auth.currentUser?.displayName ?: ""
        db.child(id).setValue(order.copy(
            orderId = id, userId = uid, userEmail = email,
            userName = name, orderedAt = System.currentTimeMillis()
        ))
            .addOnSuccessListener { onResult(true, "Order placed! ID: $id") }
            .addOnFailureListener { onResult(false, it.message ?: "Failed") }
    }

    override fun getUserOrders(userId: String, onResult: (Boolean, String, List<CafeOrder>) -> Unit) {
        db.orderByChild("userId").equalTo(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.children
                        .mapNotNull { it.getValue(CafeOrder::class.java) }
                        .sortedByDescending { it.orderedAt }
                    onResult(true, "OK", list)
                }
                override fun onCancelled(error: DatabaseError) {
                    onResult(false, error.message, emptyList())
                }
            })
    }

    override fun getAllOrders(onResult: (Boolean, String, List<CafeOrder>) -> Unit) {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = snapshot.children
                    .mapNotNull { it.getValue(CafeOrder::class.java) }
                    .sortedByDescending { it.orderedAt }
                onResult(true, "OK", list)
            }
            override fun onCancelled(error: DatabaseError) {
                onResult(false, error.message, emptyList())
            }
        })
    }

    override fun updateOrderStatus(orderId: String, status: String, onResult: (Boolean, String) -> Unit) {
        db.child(orderId).child("status").setValue(status)
            .addOnSuccessListener { onResult(true, "Status updated to $status") }
            .addOnFailureListener { onResult(false, it.message ?: "Failed") }
    }
}