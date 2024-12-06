package com.dicoding.ecofiproject.data.response

// Data class untuk response login dari server
data class LoginResponse(
    val status: String, // Status response, misalnya "success" atau "error"
    val message: String, // Pesan yang diberikan oleh server, biasanya berisi detail tentang status
    val data: UserData // Data pengguna yang dikembalikan jika login berhasil
)

// Data class untuk menyimpan data pengguna setelah login
data class UserData(
    val uid: String, // ID unik pengguna
    val name: String, // Nama pengguna
    val token: String // Token autentikasi pengguna, biasanya digunakan untuk session management
)