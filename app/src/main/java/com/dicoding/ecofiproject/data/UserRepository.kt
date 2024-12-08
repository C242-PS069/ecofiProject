package com.dicoding.ecofiproject.data

import android.util.Log
import com.dicoding.ecofiproject.data.api.ApiConfig
import com.dicoding.ecofiproject.data.pref.UserModel
import com.dicoding.ecofiproject.data.pref.UserPreference
import com.dicoding.ecofiproject.data.response.*
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(
    private val userPreference: UserPreference
) {

    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return try {
            val response = ApiConfig.getApiService().login(email, password)
            Log.d("UserRepository", "Login response: $response")
            response
        } catch (e: Exception) {
            throw Exception("Network request failed: ${e.message}")
        }
    }

    suspend fun register(name: String, email: String, password: String): Response<RegisterResponse> {
        return try {
            val response = ApiConfig.getApiService().register(name, email, password)
            Log.d("UserRepository", "Register response: $response")
            response
        } catch (e: Exception) {
            throw Exception("Network request failed: ${e.message}")
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession() = userPreference.getSession()

    suspend fun logout() {
        userPreference.logout()
    }

    // Fungsi baru untuk reset password
    suspend fun resetPassword(email: String): Response<ResetPasswordResponse> {
        return try {
            val requestBody = mapOf("email" to email)
            val response = ApiConfig.getApiService().resetPassword(requestBody)
            Log.d("UserRepository", "Reset password response: $response")
            response
        } catch (e: Exception) {
            throw Exception("Network request failed: ${e.message}")
        }
    }

    // Fungsi baru untuk edit profile
    suspend fun editProfile(
        newUsername: String?,
        oldPassword: String?,
        newPassword: String?,
        email: String
    ): Response<EditProfileResponse> {
        return try {
            val request = EditProfileRequest(newUsername, oldPassword, newPassword, email)
            val response = ApiConfig.getApiService().editProfile(request)
            Log.d("UserRepository", "Edit profile response: $response")
            response
        } catch (e: Exception) {
            throw Exception("Network request failed: ${e.message}")
        }
    }

    fun predictImage(image: MultipartBody.Part): Call<PredictionResponse> {
        // Ambil token dari session pengguna yang sudah login
        val token = userPreference.getSession().map { it.token }.toString()

        // Pastikan token valid
        if (token.isNotEmpty()) {
            // Panggil API dengan menambahkan token ke header Authorization
            return ApiConfig.getApiService().predictImage("Bearer $token", image)
        } else {
            throw Exception("User is not authenticated")
        }
    }


    // Fungsi baru untuk mendapatkan detail prediksi
    fun getRecycleDetails(id: Int): Call<RecycleDetailsResponse> {
        return ApiConfig.getApiService().getRecycleDetails(id)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(userPreference: UserPreference): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}
