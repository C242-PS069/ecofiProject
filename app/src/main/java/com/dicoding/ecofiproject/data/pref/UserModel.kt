package com.dicoding.ecofiproject.data.pref

// Data class untuk menyimpan informasi pengguna
data class UserModel(
    val name: String, // Nama pengguna
    val email: String, // Email pengguna
    val token: String, // Token autentikasi pengguna
    val isLogin: Boolean = false // Status login pengguna, default-nya adalah false
)
