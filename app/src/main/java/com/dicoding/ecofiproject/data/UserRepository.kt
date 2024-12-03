package com.dicoding.ecofiproject.data

import android.util.Log
import com.dicoding.ecofiproject.data.api.ApiConfig
import com.dicoding.ecofiproject.data.response.LoginResponse
import com.dicoding.ecofiproject.data.response.RegisterResponse
import com.dicoding.ecofiproject.data.pref.UserModel
import com.dicoding.ecofiproject.data.pref.UserPreference
import retrofit2.Response

class UserRepository private constructor(
    private val userPreference: UserPreference
) {

    // Fungsi login, mengembalikan Response<LoginResponse>
    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return try {
            val response = ApiConfig.getApiService().login(email, password)
            Log.d("UserRepository", "Login response: $response")
            response
        } catch (e: Exception) {
            throw Exception("Network request failed: ${e.message}")
        }
    }

    // Fungsi register, mengembalikan Response<RegisterResponse>
    suspend fun register(name: String, email: String, password: String): Response<RegisterResponse> {
        return try {
            val response = ApiConfig.getApiService().register(name, email, password)
            Log.d("UserRepository", "Register response: $response")
            response
        } catch (e: Exception) {
            throw Exception("Network request failed: ${e.message}")
        }
    }

    // Fungsi untuk menyimpan sesi pengguna ke preference
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    // Fungsi untuk mengambil sesi pengguna dari preference
    fun getSession() = userPreference.getSession()

    // Fungsi untuk logout, menghapus sesi dari preference
    suspend fun logout() {
        userPreference.logout()
    }

    // Singleton pattern untuk mendapatkan instance UserRepository
    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(userPreference: UserPreference): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}
