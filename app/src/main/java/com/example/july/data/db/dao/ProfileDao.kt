package com.example.july.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.july.data.db.entity.ProfileEntity

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProfileEntity(profile : ProfileEntity)

    @Query("SELECT * FROM user_profile")
    fun getProfileEntity() : LiveData<List<ProfileEntity>>

    @Update
    fun updateProfileEntity(profile: ProfileEntity)
}