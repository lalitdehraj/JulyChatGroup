package com.example.july.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.july.R
import com.example.july.databinding.FragmentEditProfileBinding
import com.example.july.ui.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfile : Fragment(R.layout.fragment_edit_profile) {

    private lateinit var binding : FragmentEditProfileBinding
    private val viewModel : ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEditProfileBinding.bind(view)

        binding.saveProfile.setOnClickListener {
            findNavController().navigate(EditProfileDirections.actionEditProfileToProfile2())
        }
    }
}