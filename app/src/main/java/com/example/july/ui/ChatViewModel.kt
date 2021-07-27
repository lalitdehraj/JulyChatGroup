package com.example.july.ui

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
    val chats: LiveData<List<Chat>>
        get() = _chats
    //private lateinit var group: String
    private val _group = MutableLiveData<String>()
    val group : LiveData<String>
    get()= _group
    init {
        //if (!group.isNullOrBlank())
//            group="D"


    }

    private fun sortedChats(unsortedChats: List<Chat>): List<Chat>? {
        if (unsortedChats.isNullOrEmpty()) return ArrayList<Chat>()
        return unsortedChats.sortedWith(compareBy { it.timestamp })
    }

    fun sendChatToDatabase(chatmsg: Chat) {
        chatRepository.sendChatToFirebase(chatmsg, group.value.toString())
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
}