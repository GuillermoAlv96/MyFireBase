package com.example.myfirebase

import android.content.Context
import android.widget.Toast

class ValidateInput {

    private fun login(context: Context, email: String, password: String): Boolean {

        if (email.isEmpty()) {
            Toast.makeText(context, "Please add Email", Toast.LENGTH_SHORT).show()
            return false
        } else if (password.isEmpty()) {
            Toast.makeText(context, "Please add password", Toast.LENGTH_SHORT).show()
            return false
        }else{
            return true
        }
    }
}