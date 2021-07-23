package com.example.july.domain.repositories

import com.example.july.domain.model.Chat

interface ChatRepository {
//    fun fetchChatFromFirebase(onSuccess : (List<Chat>) -> Unit, onFailure : () -> Unit)
    fun sendChatToFirebase(chatMessage: Chat)
}