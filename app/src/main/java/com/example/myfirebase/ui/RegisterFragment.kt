package com.example.myfirebase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myfirebase.Constants
import com.example.myfirebase.R
import com.example.myfirebase.firebase.FireStore
import com.example.myfirebase.models.User
import com.example.myfirebase.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(), FireStore.RegisterListener {

    private var mBinding: FragmentRegisterBinding? = null
    private val binding get() = mBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.buttonRegister.setOnClickListener {
            if (validate()) {
                createUser()
            }
        }

        return binding.root
    }

    /**
     * Creates user auth and firestore inf
     */
    private fun createUser() {

        val name = binding.textName.text.toString()
        val email = binding.textEmail.text.toString()
        val phone = binding.textPhone.text.toString().toLong()
        val password = binding.textPassword.text.toString()
        val user =
            User(name, email, phone, Constants.NOT_COMPLETE_PROFILE, password)

        FireStore().registry(this, requireContext(), user)
    }

    /**
     * Begins transaction to next fragment
     */
    private fun transaction() {
        val fragment = UserHomeFragment()//Navigate to second
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.navHostFragment, fragment)?.commit()
    }

    /**
     * validates all camps
     */
    private fun validate(): Boolean {

        if (binding.textName.text.isEmpty()) {

            Toast.makeText(context, "Name cant be empty", Toast.LENGTH_SHORT).show()
            return false

        } else if (binding.textEmail.text.isEmpty()) {

            Toast.makeText(context, "email cant be empty", Toast.LENGTH_SHORT).show()
            return false

        } else if (binding.textPhone.text.isEmpty()) {

            Toast.makeText(context, "Phone cant be empty", Toast.LENGTH_SHORT).show()
            return false

        } else if (binding.textPassword.text.isEmpty() && binding.textPassword.text != binding.textPassword2.text) {

            Toast.makeText(context, "Password cant be empty", Toast.LENGTH_SHORT).show()
            return false

        } else {
            return true
        }

    }


    override fun registerSuccess() {
        transaction()
    }
}