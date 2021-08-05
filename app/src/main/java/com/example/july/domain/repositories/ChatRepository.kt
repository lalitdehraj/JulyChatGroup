package com.example.july.domain.repositories

import com.example.july.data.db.entity.ChatMessageEntity
import com.example.july.data.db.entity.PasswordEntity
import com.example.july.domain.model.Chat

interface ChatRepository {
    fun fetchChatsFromFirebase(group: String, onSuccess: (List<Chat>)->Unit, onFailure: () -> Unit)
    fun sendChatToFirebase(chatMessage: Chat,group : String)
    fun sendChatToDatabase(chatMessageEntity: ChatMessageEntity)
    fun isLocked(group: String):Boolean
    fun isMatch(group: String,password:String):Boolean
    fun setPassword(passwordEntity: PasswordEntity)
}