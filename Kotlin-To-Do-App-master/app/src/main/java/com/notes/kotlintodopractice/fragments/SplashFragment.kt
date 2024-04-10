package com.notes.kotlintodopractice.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.notes.kotlintodopractice.R
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth // Firebase Authentication instance
    private lateinit var navController: NavController // Navigation controller for navigating between fragments

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false) // Inflate layout for splash screen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view) // Initialize Firebase Authentication instance and navigation controller

        val isLogin: Boolean = mAuth.currentUser != null // Check if user is logged in

        val handler = Handler(Looper.myLooper()!!) // Handler for delaying navigation
        handler.postDelayed({

            if (isLogin)
                navController.navigate(R.id.action_splashFragment_to_homeFragment) // Navigate to home fragment if user is logged in
            else
                navController.navigate(R.id.action_splashFragment_to_signInFragment) // Navigate to sign in fragment if user is not logged in

        }, 2000) // Delay navigation by 2000 milliseconds (2 seconds)
    }

    private fun init(view: View) {
        mAuth = FirebaseAuth.getInstance() // Get Firebase Authentication instance
        navController = Navigation.findNavController(view) // Initialize navigation controller with current view
    }
}
