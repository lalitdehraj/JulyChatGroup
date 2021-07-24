package com.example.july.data

import androidx.lifecycle.LiveData
import com.example.july.data.db.ChatDatabase
import com.example.july.data.db.entity.ProfileEntity
import com.example.july.domain.model.Chat
import com.example.july.domain.repositories.AuthRepository
import com.example.july.domain.repositories.ChatRepository
import com.example.july.domain.repositories.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class DataSource(
    private val firebaseAuth : FirebaseAuth,
    private val chatDatabase: ChatDatabase
) : AuthRepository, ChatRepository, ProfileRepository {

    override fun signInAsAnonymous(onSuccess: (String) -> Unit, onFailure: () -> Unit) {
        firebaseAuth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = task.result?.user?.uid
                if (!uid.isNullOrBlank())
                    onSuccess(uid)
                else
                    onFailure()
            } else {
                onFailure()
            }
        }
    }

    override fun sendChatToFirebase(chatMessage: Chat) {
    }

    override fun insertProfileInDB(profile: ProfileEntity) {
        chatDatabase.runInTransaction {
            chatDatabase.profileDao().insertProfileEntity(profile)
        }
    }

    override fun getProfileFromDB(): LiveData<List<ProfileEntity>> {
        return chatDatabase.profileDao().getProfileEntity()
    }

    override fun updateProfileInDB(profile: ProfileEntity) {
        chatDatabase.runInTransaction {
            chatDatabase.profileDao().updateProfileEntity(profile)
        }
    }
}