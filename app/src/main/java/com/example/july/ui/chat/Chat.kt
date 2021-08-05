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
import com.example.july.data.db.entity.ChatMessageEntity
import com.example.july.data.db.entity.PasswordEntity
import com.example.july.databinding.FragmentChatBinding
import com.example.july.databinding.FragmentCreateBinding
import com.example.july.databinding.FragmentPasswordBinding
import com.example.july.domain.model.Chat
import com.example.july.ui.ChatViewModel
import com.example.july.ui.ProfileViewModel
import com.example.july.utils.PUBLIC_GROUP_KEY
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Chat : Fragment(R.layout.fragment_chat){

    private lateinit var binding : FragmentChatBinding
    private val chatViewModel : ChatViewModel by viewModels()
    private val profileViewModel : ProfileViewModel by activityViewModels()
    private var group: String?= null
    private var isLocked= false

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

        setUpLock()

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
                val sender= profileViewModel.profile.value!!.name
                val timeStamp= "${System.currentTimeMillis()}"
                val uId= profileViewModel.profile.value!!.id

                val chatMsg = Chat(
                    message = msg,
                    senderName = sender,
                    timestamp = timeStamp,
                    uid = uId
                )
                val chatMessageEntity= group?.let { it1 ->
                    ChatMessageEntity(
                        it1,sender,msg,timeStamp, uId
                    )
                }


                if (chatMessageEntity != null) {
                    chatViewModel.sendChatToDatabase(chatMessageEntity)
                }
                chatViewModel.sendChatToFirebase(chatMsg)
                binding.composeMessageLayout.chatMessage.setText("")


            }
        }

        chatViewModel.chats.observe(viewLifecycleOwner) { chats ->
            if (::chatAdapter.isInitialized){
                chatAdapter.submitList(chats?.reversed() ?: ArrayList())
            }
        }

        binding.gotoLock.setOnClickListener(){
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.fragment_password, null)
            val bindo = FragmentPasswordBinding.bind(view)

            bindo.submitPassword.setOnClickListener(){
                val passwordEntity= PasswordEntity(group!!,bindo.getPassword.text.toString(),true)
                chatViewModel.setPassword(passwordEntity)
            }
            dialog.setContentView(view)
            dialog.show()
        }

    }

    private fun setUpLock() {
        isLocked = chatViewModel.isLocked(group!!)
        if (!isLocked) {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.fragment_password, null)
            val bindo = FragmentPasswordBinding.bind(view)
            dialog.setContentView(view)
            dialog.show()
            bindo.submitPassword.setOnClickListener() {
                if (chatViewModel.isMatch(group!!, bindo.getPassword.text.toString()))
                    dialog.dismiss()
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