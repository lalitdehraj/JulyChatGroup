package com.example.july.domain.repositories

import com.example.july.domain.model.Chat

interface ChatRepository {
    fun fetchChatsFromFirebase(group: String, onSuccess: (List<Chat>)->Unit, onFailure: () -> Unit)
    fun sendChatToFirebase(chatMessage: Chat,group : String)
}