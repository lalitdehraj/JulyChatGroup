package com.example.july.domain.repositories

import android.content.Context

interface AuthRepository {
    fun signInAsAnonymous(onSuccess : () -> Unit, onFailure : () -> Unit)
}