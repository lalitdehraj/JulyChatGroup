package com.example.july.ui.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.july.R
import com.example.july.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Chat : Fragment(R.layout.fragment_chat){

    private lateinit var binding : FragmentChatBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentChatBinding.bind(view)

        binding.gotoProfile.setOnClickListener {
            findNavController().navigate(ChatDirections.actionChatToProfile2())
        }
    }
}