package com.dicoding.ecofiproject.data.response

data class ResetPasswordResponse(
    val status: String,
    val message: String
)

data class EditProfileRequest(
    val newUsername: String?,
    val oldPassword: String?,
    val newPassword: String?,
    val email: String
)

data class EditProfileResponse(
    val status: String,
    val message: String
)
