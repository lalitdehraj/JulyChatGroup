package com.example.july.ui

import androidx.lifecycle.*
import com.example.july.data.db.entity.ProfileEntity
import com.example.july.domain.repositories.ChatRepository
import com.example.july.domain.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    val profile: LiveData<ProfileEntity> = Transformations.map(profileRepository.getProfileFromDB()){
        it[0]
    }

    fun insertUserInDB(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val profile = ProfileEntity(id = id, name = "Anonymous", bio = "No bio")
                profileRepository.insertProfileInDB(profile)
            }
        }
    }

    fun updateUserInDB(name: String, bio: String) {
        val uid = profile.value!!.id
        val updatedProfile = ProfileEntity(uid, name, bio)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                profileRepository.updateProfileInDB(updatedProfile)
            }
        }
    }

}