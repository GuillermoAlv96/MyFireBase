package com.example.myfirebase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfirebase.R
import com.example.myfirebase.databinding.FragmentUserHomeBinding
import com.example.myfirebase.firebase.FireStore
import com.example.myfirebase.models.User

class UserHomeFragment : Fragment(), FireStore.UserInfoSuccess {

    private var mBinding: FragmentUserHomeBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentUserHomeBinding.inflate(inflater, container, false)
        binding.imageButtonEdit.setOnClickListener {
            transaction()
        }
        FireStore().getCurrentUserDetails(this)
        return binding.root
    }

    /**
     * Begins transaction to next fragment
     */
    private fun transaction() {
        val fragment = EditFragment()//Navigate to second
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.navHostFragment, fragment)?.commit()
    }

    override fun userInfoComplete(user: User) {
        binding.textName.text = user.name
        binding.textEmail.text = user.email
        binding.textPhone.text = user.phone.toString()
    }


}