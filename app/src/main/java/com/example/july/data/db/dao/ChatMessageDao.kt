package com.example.july.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import com.example.july.data.db.entity.ChatMessageEntity

@Dao
interface ChatMessageDao {
    @Insert(onConflict = IGNORE)
    fun insertChatMessage(chatMessageEntity: ChatMessageEntity)
}