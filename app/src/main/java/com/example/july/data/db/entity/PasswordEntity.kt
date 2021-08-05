package com.example.july.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password_table")
class PasswordEntity (
    @PrimaryKey
    val groupName: String,
    val password: String,
    val passwordSetted: Boolean
        )