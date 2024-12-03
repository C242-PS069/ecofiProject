package com.dicoding.ecofiproject.data

import android.util.Log
import com.dicoding.ecofiproject.data.api.ApiConfig
import com.dicoding.ecofiproject.data.response.LoginResponse
import com.dicoding.ecofiproject.data.response.RegisterResponse
import com.dicoding.ecofiproject.data.pref.UserModel
import com.dicoding.ecofiproject.data.pref.UserPreference
import retrofit2.Response

// Kelas UserRepository untuk menangani operasi terkait pengguna seperti login, registrasi, dan sesi
class UserRepository private constructor(
    private val userPreference: UserPreference
) {

    // Fungsi login, mengembalikan Response<LoginResponse>
    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return try {
            // Mengirim permintaan login ke API menggunakan ApiService
            val response = ApiConfig.getApiService().login(email, password)
            Log.d("UserRepository", "Login response: $response") // Menampilkan log respons login
            response
        } catch (e: Exception) {
            // Menangani exception jika permintaan gagal
            throw Exception("Network request failed: ${e.message}")
        }
    }

    // Fungsi register, mengembalikan Response<RegisterResponse>
    suspend fun register(name: String, email: String, password: String): Response<RegisterResponse> {
        return try {
            // Mengirim permintaan registrasi ke API menggunakan ApiService
            val response = ApiConfig.getApiService().register(name, email, password)
            Log.d("UserRepository", "Register response: $response") // Menampilkan log respons registrasi
            response
        } catch (e: Exception) {
            // Menangani exception jika permintaan gagal
            throw Exception("Network request failed: ${e.message}")
        }
    }

    // Fungsi untuk menyimpan sesi pengguna ke preference
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user) // Memanggil fungsi saveSession() dari UserPreference untuk menyimpan data pengguna
    }

    // Fungsi untuk mengambil sesi pengguna dari preference
    fun getSession() = userPreference.getSession() // Mengambil data sesi pengguna dengan memanggil getSession() dari UserPreference

    // Fungsi untuk logout, menghapus sesi dari preference
    suspend fun logout() {
        userPreference.logout() // Menghapus sesi pengguna dengan memanggil logout() dari UserPreference
    }

    // Singleton pattern untuk mendapatkan instance UserRepository
    companion object {
        @Volatile
        private var instance: UserRepository? = null

        // Fungsi untuk mendapatkan instance tunggal dari UserRepository
        fun getInstance(userPreference: UserPreference): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference) // Membuat instance jika belum ada
            }.also { instance = it } // Menyimpan instance setelah dibuat
    }
}
