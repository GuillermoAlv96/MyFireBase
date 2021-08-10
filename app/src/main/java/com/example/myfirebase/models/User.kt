package com.example.myfirebase.models


data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: Long,
    val profileCompleted: Long,
    val password: String)
