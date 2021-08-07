package com.example.myfirebase

import android.provider.ContactsContract

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: Int,
    val password: String)
