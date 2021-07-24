package com.example.july.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.july.R
import com.example.july.databinding.FragmentProfileBinding
import com.example.july.ui.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Profile : Fragment(R.layout.fragment_profile) {

    private lateinit var binding : FragmentProfileBinding
    private val viewModel : ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)

        binding.editProfile.setOnClickListener {
            findNavController().navigate(ProfileDirections.actionProfile2ToEditProfile())
        }
    }
}