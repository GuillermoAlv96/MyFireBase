package com.example.myfirebase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myfirebase.R
import com.example.myfirebase.databinding.FragmentEditBinding
import com.example.myfirebase.databinding.FragmentLoginBinding
import com.example.myfirebase.firebase.FireStore
import com.example.myfirebase.models.User

class EditFragment : Fragment(), FireStore.UserEditSuccess, FireStore.UserInfoSuccess {

    private var mBinding: FragmentEditBinding? = null
    private val binding get() = mBinding!!

    override fun onStart() {
        FireStore().getCurrentUserDetails(this)
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentEditBinding.inflate(inflater, container, false)

        binding.editButton.setOnClickListener {

            if (checkCamps()) {
                val name = binding.textName.text.toString()
                val email = binding.textEmail.text.toString()
                val phone = binding.textPhone.text.toString()
                val user = User(name,email, phone.toLong())
                FireStore().editUser(this, user)
            }
        }
        return binding.root
    }

    override fun editUserSuccess() {

        transaction()
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
     * Check camps are not empty
     */
    private fun checkCamps(): Boolean {
        val check = true
        val name = binding.textName.text.toString()
        val email = binding.textEmail.text.toString()
        val phone = binding.textPhone.text.toString()
        if (name.isEmpty()) {
            Toast.makeText(context, "Name cant be empty", Toast.LENGTH_SHORT).show()
            return false
        } else if (email.isEmpty()) {
            Toast.makeText(context, "Email cant be empty", Toast.LENGTH_SHORT).show()
            return false
        } else if (phone.isEmpty()) {
            Toast.makeText(context, "Phone cant be empty", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return check
        }
    }

    override fun userInfoComplete(user: User) {
        binding.textName.setText(user.name)
        binding.textEmail.setText(user.email)
        binding.textPhone.setText(user.phone.toString())
    }


}
