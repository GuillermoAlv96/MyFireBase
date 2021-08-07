package com.example.myfirebase

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfirebase.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private var mBinding: FragmentLoginBinding? = null
    private val binding get() = mBinding!!

    private lateinit var auth: FirebaseAuth

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentLoginBinding.inflate(inflater, container, false)


        binding.buttonLogin.setOnClickListener {

            val email = binding.emailText.text.toString()
            val password = binding.passwordText.text.toString()

            FireStore().login(requireContext(), email,password)
        }

        binding.buttonRegistry.setOnClickListener {
            val fragment = RegisterFragment()//Navigate to second
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.navHostFragment,fragment)?.commit()
        }

        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }


}





