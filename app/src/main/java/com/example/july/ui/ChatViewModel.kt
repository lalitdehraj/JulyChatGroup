package com.example.july.ui

import android.text.BoringLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.july.data.db.entity.ChatMessageEntity
import com.example.july.data.db.entity.PasswordEntity
import com.example.july.domain.model.Chat
import com.example.july.domain.repositories.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _chats = MutableLiveData<List<Chat>>()
    val chats: LiveData<List<Chat>>
        get() = _chats

    private val _group = MutableLiveData<String>()
    val group: LiveData<String>
        get() = _group



    private fun sortedChats(unsortedChats: List<Chat>): List<Chat>? {
        if (unsortedChats.isNullOrEmpty()) return ArrayList<Chat>()
        return unsortedChats.sortedWith(compareBy { it.timestamp })
    }

    fun sendChatToDatabase( chatMessageEntity: ChatMessageEntity) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                chatRepository.sendChatToDatabase(chatMessageEntity)
            }
        }
    }

    fun sendChatToFirebase(chatmsg: Chat){
        chatRepository.sendChatToFirebase(chatmsg,group.value.toString())
    }

    fun setGroup(group: String) {
        this._group.value = group
        chatRepository.fetchChatsFromFirebase(this.group.value.toString(),
            onSuccess = { unsortedChats ->
                _chats.value = sortedChats(unsortedChats)
            },
            onFailure = {

            }
        )
    }
    fun setPassword(passwordEntity: PasswordEntity){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
        chatRepository.setPassword(passwordEntity)}}
    }
    fun isLocked(group: String):Boolean{
        val isLocked= false
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
        chatRepository.isLocked(group)}}
        return isLocked
    }
    fun isMatch(groupName:String,password:String):Boolean{
        val isMatch=false
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
        chatRepository.isMatch(groupName,password)}}
        return isMatch
    }
}