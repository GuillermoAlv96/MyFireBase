package com.example.myfirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myfirebase.databinding.FragmentMainBinding

class RegisterFragment : Fragment() {

    private var mBinding: FragmentMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentMainBinding.inflate(inflater, container, false)


        binding.buttonRegister.setOnClickListener {
            if (validate()) {
                val name = binding.textName.text.toString()
                val email = binding.textEmail.text.toString()
                val phone = binding.textPhone.text.toString().toInt()
                val password = binding.textPassword.text.toString()
                val user = User("null", name, email, phone, password)
                FireStore().registry(requireContext(), user)
                FireStore().registerUserDatabase(requireContext(),user)
            }
        }

        return binding.root
    }

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
}