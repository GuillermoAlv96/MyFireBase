package com.example.myfirebase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfirebase.R
import com.example.myfirebase.databinding.FragmentEditBinding
import com.example.myfirebase.databinding.FragmentLoginBinding
import com.example.myfirebase.firebase.FireStore
import com.example.myfirebase.models.User

class EditFragment : Fragment(), FireStore.UserEditSuccess {

    private var mBinding: FragmentEditBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentEditBinding.inflate(inflater, container, false)

        binding.editButton.setOnClickListener {

            val name = binding.textName.text.toString()
            val email = binding.textEmail.text.toString()
            val phone = binding.textPhone.text.toString()
            val user = User(
                "",
                name,
                email,
                phone.toLong(),
                2,
                ""
            )
            FireStore().editUser(this, user)
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


}
