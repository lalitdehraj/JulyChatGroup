package com.example.july.ui.chat

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.july.R
import com.example.july.databinding.FragmentChatBinding
import com.example.july.domain.model.Chat
import com.example.july.ui.ChatViewModel
import com.example.july.ui.ProfileViewModel
import com.example.july.utils.PUBLIC_GROUP_KEY
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Chat : Fragment(R.layout.fragment_chat){

    private lateinit var binding : FragmentChatBinding
    private val chatViewModel : ChatViewModel by viewModels()
    private val profileViewModel : ProfileViewModel by activityViewModels()
    private var group: String?= null

    private lateinit var chatAdapter : ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(PUBLIC_GROUP_KEY)?.let { gId->
            group= gId
        }
        group?.let { it1 -> chatViewModel.setGroup(it1) }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentChatBinding.bind(view)


        binding.gotoProfile.setOnClickListener {
            findNavController().navigate(ChatDirections.actionChatToProfile2())
        }

        profileViewModel.profile.observe(viewLifecycleOwner){ profile ->
            if (profile != null){
                initAdapter(profile.id)
            }
        }

        binding.composeMessageLayout.chatSend.setOnClickListener {
            val msg = binding.composeMessageLayout.chatMessage.text.toString()
            if (msg.isNotBlank()){
                val chatMsg = Chat(
                    message = msg,
                    senderName = profileViewModel.profile.value!!.name,
                    timestamp = "${System.currentTimeMillis()}",
                    uid = profileViewModel.profile.value!!.id
                )

                chatViewModel.sendChatToDatabase(chatMsg)
                binding.composeMessageLayout.chatMessage.setText("")
            }
        }

        chatViewModel.chats.observe(viewLifecycleOwner) { chats ->
            if (::chatAdapter.isInitialized){
                chatAdapter.submitList(chats?.reversed() ?: ArrayList())
            }
        }

    }

    private fun initAdapter(id: String) {
        if (!::chatAdapter.isInitialized){
            chatAdapter = ChatAdapter(layoutInflater, id)


            binding.chats.apply {
                adapter = chatAdapter
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    true
                )
            }
        }
    }
}