package com.example.himalayanpunch.utils

import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// ── Firebase Listener Manager ──────────────────────────────────────────────────
// Manages lifecycle of Firebase realtime database listeners to prevent memory leaks.

object FirebaseListenerManager {
    private val activeListeners = mutableMapOf<String, Pair<com.google.firebase.database.DatabaseReference, ValueEventListener>>()

    fun register(key: String, path: String, listener: ValueEventListener) {
        val ref = Firebase.database.reference.child(path)
        ref.addValueEventListener(listener)
        activeListeners[key] = Pair(ref, listener)
    }

    fun remove(key: String) {
        activeListeners[key]?.let { (ref, listener) ->
            ref.removeEventListener(listener)
        }
        activeListeners.remove(key)
    }

    fun removeAll() {
        activeListeners.forEach { (_, pair) ->
            pair.first.removeEventListener(pair.second)
        }
        activeListeners.clear()
    }
}