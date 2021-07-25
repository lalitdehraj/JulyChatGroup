package com.example.july.ui.splash

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.july.R
import com.example.july.domain.repositories.AuthRepository
import com.example.july.ui.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Splash : Fragment(R.layout.fragment_splash) {

    private val profileViewModel : ProfileViewModel by activityViewModels()
    @Inject lateinit var authRepository : AuthRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(requireContext(), "Logging in...", Toast.LENGTH_SHORT).show()

        authRepository.signInAsAnonymous(
            onSuccess = { uid ->
                Toast.makeText(requireContext(), "Logged in", Toast.LENGTH_SHORT).show()
                profileViewModel.insertUserInDB(uid)
                findNavController().navigate(SplashDirections.actionSplashToChat())
            },

            onFailure = {
                Toast.makeText(requireContext(), "Login failed!", Toast.LENGTH_SHORT).show()

            }
        )

    }
}