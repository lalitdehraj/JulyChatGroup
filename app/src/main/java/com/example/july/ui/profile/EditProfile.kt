package com.example.july.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.july.R
import com.example.july.databinding.FragmentEditProfileBinding
import com.example.july.ui.ProfileViewModel
import com.example.july.utils.checkIfValidBio
import com.example.july.utils.checkIfValidName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfile : Fragment(R.layout.fragment_edit_profile) {

    private lateinit var binding : FragmentEditProfileBinding
    private val viewModel : ProfileViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEditProfileBinding.bind(view)

        binding.saveProfile.setOnClickListener {
            val enteredName = binding.personName.text.toString()
            val enteredBio = binding.personBio.text.toString()

            if (checkIfValidName(enteredName)){
                if (checkIfValidBio(enteredBio)){
                    // Update profile
                    viewModel.updateUserInDB(enteredName, enteredBio)
                    requireActivity().onBackPressed()
//                    findNavController().navigate(EditProfileDirections.actionEditProfileToProfile2())
                }else{
                    Toast.makeText(requireContext(), "Invalid bio", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "Invalid name", Toast.LENGTH_SHORT).show()
            }

        }

        viewModel.profile.observe(viewLifecycleOwner) { profile ->
            if (profile != null){
                binding.personName.setText(profile.name)
                binding.personBio.setText(profile.bio)
            }
        }
    }
}