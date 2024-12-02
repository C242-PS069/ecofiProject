package com.dicoding.ecofiproject.data

import android.util.Log
import com.dicoding.ecofiproject.data.api.ApiConfig
import com.dicoding.ecofiproject.data.response.LoginResponse
import com.dicoding.ecofiproject.data.response.RegisterResponse
import com.dicoding.ecofiproject.data.pref.UserModel
import com.dicoding.ecofiproject.data.pref.UserPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class UserRepository private constructor(
    private val userPreference: UserPreference
) {
    suspend fun login(email: String, password: String): Call<LoginResponse> {
        return withContext(Dispatchers.IO) {
            val response = ApiConfig.getApiService("").login(email, password)
            Log.d("UserRepository", "Login response: $response")
            response
        }
    }

    suspend fun register(name: String, email: String, password: String): Call<RegisterResponse> {
        return withContext(Dispatchers.IO) {
            try {
                ApiConfig.getApiService("").register(name, email, password)
            } catch (e: Exception) {
                throw Exception("Network request failed: ${e.message}")
            }
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession().map { user ->
            Log.d("UserRepository", "getSession: User data from preferences: $user")
            user
        }
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}
