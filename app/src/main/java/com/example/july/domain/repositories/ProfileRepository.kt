package com.example.july.domain.repositories

import androidx.lifecycle.LiveData
import com.example.july.data.db.entity.ProfileEntity

interface ProfileRepository {
    fun insertProfileInDB(profile : ProfileEntity)
    fun getProfileFromDB() : LiveData<List<ProfileEntity>>
    fun updateProfileInDB(profile: ProfileEntity)
}