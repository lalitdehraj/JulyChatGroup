package com.example.july.ui

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.july.domain.model.Chat
import com.example.july.domain.repositories.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _chats = MutableLiveData<List<Chat>>()
    val chats : LiveData<List<Chat>>
        get() = _chats

    init {
        chatRepository.fetchChatsFromFirebase(
            onSuccess = {
                        _chats.value = it
            },
            onFailure = {

            }
        )
    }

    fun sendChatToDatabase(chatmsg : Chat){
        chatRepository.sendChatToFirebase(chatmsg)
    }
}