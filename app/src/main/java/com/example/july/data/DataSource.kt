package com.example.july.data

import androidx.lifecycle.LiveData
import com.example.july.data.db.ChatDatabase
import com.example.july.data.db.entity.ChatMessageEntity
import com.example.july.data.db.entity.GroupEntity
import com.example.july.data.db.entity.PasswordEntity
import com.example.july.data.db.entity.ProfileEntity
import com.example.july.domain.model.Chat
import com.example.july.domain.repositories.AuthRepository
import com.example.july.domain.repositories.ChatRepository
import com.example.july.domain.repositories.GroupRepository
import com.example.july.domain.repositories.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class DataSource(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
    private val chatDatabase: ChatDatabase
) : AuthRepository, ChatRepository, ProfileRepository,GroupRepository {

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

    override fun fetchChatsFromFirebase(group: String, onSuccess: (List<Chat>) -> Unit, onFailure: () -> Unit) {
        val groupChatRef = firebaseDatabase.getReference(group)

        groupChatRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentChatList: ArrayList<Chat> = ArrayList()

                val chatMap: HashMap<String, HashMap<String, String>> =
                    snapshot.value as? HashMap<String, HashMap<String, String>> ?: return
                chatMap.map { response ->
                    val chat: Chat =
                        Chat(
                            senderName = response.value["senderName"]!!,
                            message = response.value["message"]!!,
                            timestamp = response.value["timestamp"]!!,
                            uid = response.value["uid"]!!
                        )

                    currentChatList.add(chat)
                }
                onSuccess(currentChatList)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    override fun sendChatToFirebase(chatMessage: Chat,group: String) {
        val groupChatRef = firebaseDatabase.getReference(group).push()
        groupChatRef.setValue(chatMessage)
    }

    override fun sendChatToDatabase(chatMessageEntity: ChatMessageEntity) {
        chatDatabase.chatMessageDao().insertChatMessage(chatMessageEntity)
    }

    override fun isLocked(group: String): Boolean {
        return chatDatabase.passwordDao().isPasswordSet(group)
    }

    override fun isMatch(group: String, password: String): Boolean {
        val passwordSetted= chatDatabase.passwordDao().isPasswordMatch(group)
        if(passwordSetted.equals(password))
            return true
        return false
    }

    override fun setPassword(passwordEntity: PasswordEntity) {
        chatDatabase.passwordDao().insertPassword(passwordEntity)
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

    override fun fetchGroupsFromDatabase(): LiveData<List<GroupEntity>> {
        return chatDatabase.groupDao().getGroupEntity()
    }

    override fun insertGroupIntoDatabse(groupEntity: GroupEntity) {

        chatDatabase.groupDao().insertGroupEntity(groupEntity)
    }
}