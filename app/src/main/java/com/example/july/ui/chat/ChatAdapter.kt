package com.example.july.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.july.R
import com.example.july.databinding.ReceivedChatListItemBinding
import com.example.july.databinding.SentChatListItemBinding
import com.example.july.domain.model.Chat

class ChatAdapter(
    private val layoutInflater: LayoutInflater,
    private val ourUid: String
) : ListAdapter<Chat, RecyclerView.ViewHolder>(DiffUtilsCallback) {

    object DiffUtilsCallback : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.uid == newItem.uid && oldItem.timestamp == newItem.timestamp
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }
    }

    class SentChatViewHolder(private val binding: SentChatListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatMessage: Chat) {
            binding.chat = chatMessage
            binding.executePendingBindings()
        }
    }

    class ReceivedChatViewHolder(private val binding: ReceivedChatListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatMessage: Chat) {
            binding.chat = chatMessage
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.sent_chat_list_item -> {
                SentChatViewHolder(
                    SentChatListItemBinding.inflate(layoutInflater, parent, false)
                )
            }

            R.layout.received_chat_list_item -> {
                ReceivedChatViewHolder(
                    ReceivedChatListItemBinding.inflate(layoutInflater, parent, false)
                )
            }

            else -> {
                throw IllegalArgumentException("Unknown viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.sent_chat_list_item -> {
                (holder as SentChatViewHolder).bind(getItem(position))
            }
            R.layout.received_chat_list_item -> {
                (holder as ReceivedChatViewHolder).bind(getItem(position))
            }
            else -> {
                throw IllegalArgumentException("Unknown viewType")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val chatMessage: Chat = getItem(position)
        return if (chatMessage.uid == ourUid) {
            R.layout.sent_chat_list_item
        } else {
            R.layout.received_chat_list_item
        }
    }
}