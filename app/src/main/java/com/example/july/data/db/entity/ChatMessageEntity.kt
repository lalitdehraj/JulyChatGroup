package com.example.july.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_table")
class ChatMessageEntity(
    val groupName: String,
    val senderName : String,
    val message : String,
    @PrimaryKey
    val timestamp : String,
    val uId : String
)