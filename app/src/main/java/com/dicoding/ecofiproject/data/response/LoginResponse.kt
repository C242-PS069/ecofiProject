package com.dicoding.ecofiproject.data.response

data class LoginResponse(
    val status: String,
    val message: String,
    val data: UserData
)

data class UserData(
    val uid: String,
    val name: String,
    val token: String
)
