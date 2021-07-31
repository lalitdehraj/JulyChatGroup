package com.example.july.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "group_table")
class GroupEntity(
    @PrimaryKey
    @ColumnInfo(name = "groupName")
    val groupName: String
)
