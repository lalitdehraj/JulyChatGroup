package com.example.july.data.network

import com.example.july.domain.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth

data class FirebaseAuthentication(
    private val firebaseAuth : FirebaseAuth
) : AuthRepository {

    override fun signInAsAnonymous(onSuccess: () -> Unit, onFailure: () -> Unit) {
        firebaseAuth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onFailure()
            }
        }
    }
}