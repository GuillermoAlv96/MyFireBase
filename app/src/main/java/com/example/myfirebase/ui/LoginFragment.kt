package com.example.myfirebase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myfirebase.firebase.FireStore
import com.example.myfirebase.R
import com.example.myfirebase.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


 class LoginFragment : Fragment(), FireStore.LoginListener {

    private var mBinding: FragmentLoginBinding? = null
    private val binding get() = mBinding!!

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        userSession()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.buttonLogin.setOnClickListener {
            login()
        }
        binding.buttonRegistry.setOnClickListener {
            register()
        }

        return binding.root
    }


    private fun userSession() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
            val fragment = UserHomeFragment()//Navigate to second
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.navHostFragment, fragment)?.commit()
        }
    }

    /**
     * Sends user to RegisterFragment
     */
    private fun register() {
        val fragment = RegisterFragment()//Navigate to second
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.navHostFragment, fragment)?.commit()
    }

    /**
     * Logs user
     */
    private fun login() {

        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        FireStore().login(this, requireContext(), email, password)

    }

    /**
     * Checks if email or password are null
     */
    private fun loginNotNull(): Boolean {
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(context, "Please add email", Toast.LENGTH_SHORT).show()
            return false
        } else if (password.isEmpty()) {
            Toast.makeText(context, "Please add password", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }


    /**
     * A function to notify user that logged in success and get the user details from the FireStore database after authentication.
     */

    fun userLoggedInSuccess() {

        val fragment = UserHomeFragment()//Navigate to second
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.navHostFragment, fragment)?.commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun loginSuccess() {
        userLoggedInSuccess()
    }


}





