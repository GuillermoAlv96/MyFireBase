package com.example.myfirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfirebase.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private var mBinding: FragmentLoginBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        //  mBinding = LoginFragment.inflate(inflater,container,false)

        val view = binding.root
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }


}





