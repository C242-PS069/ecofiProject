package com.dicoding.ecofiproject.data

import android.util.Log
import com.dicoding.ecofiproject.data.api.ApiConfig
import com.dicoding.ecofiproject.data.pref.UserModel
import com.dicoding.ecofiproject.data.pref.UserPreference
import com.dicoding.ecofiproject.data.response.*
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


    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(userPreference: UserPreference): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}