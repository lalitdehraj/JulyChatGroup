package com.example.july.domain.repositories

interface AuthRepository {
    fun signInAsAnonymous(onSuccess : (String) -> Unit, onFailure : () -> Unit)
}