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
import com.notes.kotlintodopractice.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private lateinit var navController: NavController // Navigation controller for navigating between fragments
    private lateinit var mAuth: FirebaseAuth // Firebase Authentication instance
    private lateinit var binding: FragmentSignUpBinding // View binding for this fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false) // Inflate layout using view binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view) // Initialize navigation controller and Firebase Authentication instance

        // Navigate to sign in fragment when sign in text is clicked
        binding.textViewSignIn.setOnClickListener {
            navController.navigate(R.id.action_signUpFragment_to_signInFragment) // Using navigation controller to navigate to sign in fragment
        }

        // Attempt to register user when next button is clicked
        binding.nextBtn.setOnClickListener {
            val email = binding.emailEt.text.toString() // Get email from email edit text
            val pass = binding.passEt.text.toString() // Get password from password edit text
            val verifyPass = binding.verifyPassEt.text.toString() // Get verified password from verify password edit text

            if (email.isNotEmpty() && pass.isNotEmpty() && verifyPass.isNotEmpty()) { // Check if email, password, and verify password are not empty
                if (pass == verifyPass) { // Check if password matches the verified password
                    registerUser(email, pass) // Register user with provided credentials
                } else {
                    Toast.makeText(context, "Password is not same", Toast.LENGTH_SHORT).show() // Show error message if passwords don't match
                }
            } else
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show() // Show error message if any field is empty
        }
    }

    private fun registerUser(email: String, pass: String) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful)
                navController.navigate(R.id.action_signUpFragment_to_homeFragment) // Navigate to home fragment if registration is successful
            else
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show() // Show error message if registration fails
        }
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view) // Initialize navigation controller with current view
        mAuth = FirebaseAuth.getInstance() // Get Firebase Authentication instance
    }
}
