package com.example.july.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.example.july.data.db.entity.PasswordEntity

@Dao
interface PasswordDao {
    @Query("Select passwordSetted from password_table Where groupName=:groupName")
    fun isPasswordSet(groupName: String):Boolean

    @Query("Select password from password_table where groupName=:groupName")
    fun isPasswordMatch(groupName: String):String

    @Insert(onConflict = IGNORE)
    fun insertPassword(passwordEntity: PasswordEntity)
}