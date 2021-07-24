package com.example.july.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class ProfileEntity(
    @PrimaryKey
    @ColumnInfo(name = "uid")
    val id : String,

    @ColumnInfo(name = "name")
    val name : String,

    @ColumnInfo(name = "bio")
    val bio : String,

)
