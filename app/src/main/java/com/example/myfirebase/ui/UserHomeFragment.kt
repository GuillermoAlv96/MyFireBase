package com.example.myfirebase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfirebase.databinding.FragmentUserHomeBinding
import com.example.myfirebase.firebase.FireStore

class UserHomeFragment : Fragment(), FireStore.UserInfoSuccess {

    private var mBinding: FragmentUserHomeBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentUserHomeBinding.inflate(inflater, container, false)

        FireStore().getCurrentUserDetails(this)
        return binding.root
    }


    override fun userInfoComplete(name: String, email: String, phone: Int) {
        binding.textName.text = name
        binding.textEmail.text = email
        binding.textPhone.text = phone.toString()
    }


}