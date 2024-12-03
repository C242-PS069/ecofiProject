package com.dicoding.ecofiproject.data.response

// Data class untuk response registrasi dari server
data class RegisterResponse(
    val status: String, // Status response, misalnya "success" atau "error"
    val message: String // Pesan yang diberikan oleh server, biasanya berisi detail tentang status registrasi
)
