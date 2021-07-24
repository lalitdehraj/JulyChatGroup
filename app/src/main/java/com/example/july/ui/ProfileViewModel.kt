package com.example.july.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.july.data.db.entity.ProfileEntity
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
        if (!it.isNullOrEmpty()){
            it[0]
        }else{
            ProfileEntity(id = "dfndjnfjdnfdjnfdfndjfn", name = "Anonymous", bio = "No bio")
        }
    }

    fun insertUserInDB(id : String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val profile = ProfileEntity(id = id, name = "Anonymous", bio = "No bio")
                profileRepository.insertProfileInDB(profile)
            }
        }
    }

}