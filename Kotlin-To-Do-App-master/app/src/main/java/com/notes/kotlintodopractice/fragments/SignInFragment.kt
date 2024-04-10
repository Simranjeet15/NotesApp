package com.notes.kotlintodopractice.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.notes.kotlintodopractice.R
import com.notes.kotlintodopractice.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    private lateinit var navController: NavController // Navigation controller for navigating between fragments
    private lateinit var mAuth: FirebaseAuth // Firebase Authentication instance
    private lateinit var binding: FragmentSignInBinding // View binding for this fragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false) // Inflate layout using view binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view) // Initialize navigation controller and Firebase Authentication instance

        // Navigate to sign up fragment when sign up text is clicked
        binding.textViewSignUp.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment) // Using navigation controller to navigate to sign up fragment
        }

        // Attempt to log in when next button is clicked
        binding.nextBtn.setOnClickListener {
            val email = binding.emailEt.text.toString() // Get email from email edit text
            val pass = binding.passEt.text.toString() // Get password from password edit text

            if (email.isNotEmpty() && pass.isNotEmpty()) // Check if email and password are not empty
                loginUser(email, pass) // Attempt to log in user with provided credentials
            else
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show() // Show error message if email or password is empty
        }
    }

    private fun loginUser(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful)
                navController.navigate(R.id.action_signInFragment_to_homeFragment) // Navigate to home fragment if login is successful
            else
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show() // Show error message if login fails
        }
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view) // Initialize navigation controller with current view
        mAuth = FirebaseAuth.getInstance() // Get Firebase Authentication instance
    }

}
