package com.example.himalayanpunch.repository

import com.example.himalayanpunch.model.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BookingRepoImpl : BookingRepo {
    private val db = Firebase.database.reference.child("menuItems")

    override fun getAllItems(onResult: (Boolean, String, List<MenuItem>) -> Unit) {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = snapshot.children.mapNotNull { it.getValue(MenuItem::class.java) }
                onResult(true, "OK", list)
            }
            override fun onCancelled(error: DatabaseError) {
                onResult(false, error.message, emptyList())
            }
        })
    }

    override fun addItem(item: MenuItem, onResult: (Boolean, String) -> Unit) {
        val id = db.push().key ?: return onResult(false, "Failed to generate ID")
        db.child(id).setValue(item.copy(itemId = id))
            .addOnSuccessListener { onResult(true, "Item added successfully") }
            .addOnFailureListener { onResult(false, it.message ?: "Failed") }
    }

    override fun updateItem(item: MenuItem, onResult: (Boolean, String) -> Unit) {
        db.child(item.itemId).setValue(item)
            .addOnSuccessListener { onResult(true, "Updated") }
            .addOnFailureListener { onResult(false, it.message ?: "Failed") }
    }

    override fun deleteItem(itemId: String, onResult: (Boolean, String) -> Unit) {
        db.child(itemId).removeValue()
            .addOnSuccessListener { onResult(true, "Deleted") }
            .addOnFailureListener { onResult(false, it.message ?: "Failed") }
    }
}