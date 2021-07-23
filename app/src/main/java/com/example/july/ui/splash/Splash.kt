package com.example.july.ui.splash

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.july.R
import com.example.july.data.network.FirebaseAuthentication
import com.google.firebase.auth.FirebaseAuth

class Splash : Fragment(R.layout.fragment_splash) {

    private val firebaseAuthentication : FirebaseAuthentication by lazy {
        FirebaseAuthentication(FirebaseAuth.getInstance())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(requireContext(), "Logging in...", Toast.LENGTH_SHORT).show()

        firebaseAuthentication.signInAsAnonymous(
            onSuccess = {
                Toast.makeText(requireContext(), "Logged in", Toast.LENGTH_SHORT).show()
                findNavController().navigate(SplashDirections.actionSplashToChat())
            },

            onFailure = {
                Toast.makeText(requireContext(), "Login failed!", Toast.LENGTH_SHORT).show()

            }
        )

    }
}