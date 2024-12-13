package com.dicoding.ecofiproject.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.ecofiproject.data.UserRepository
import com.dicoding.ecofiproject.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user) // Menyimpan sesi menggunakan repository
            Log.d("UserRepository", "Saving session with token: ${user.token}") // Menampilkan log untuk debugging
        }
    }

    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val token = loginResponse?.data?.token
                    if (!token.isNullOrEmpty()) {
                        val userModel = UserModel(
                            name = loginResponse.data.name,
                            email = email,
                            token = token,
                            isLogin = true
                        )
                        saveSession(userModel)
                        Log.d("LoginViewModel", "Login successful: Token=$token")
                        callback(true, token)
                    } else {
                        Log.e("LoginViewModel", "Login failed: Token is null or empty")
                        callback(false, "Token is null or empty")
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("LoginViewModel", "Login failed: $errorMessage")
                    callback(false, errorMessage)
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Login error: ${e.message}", e)
                callback(false, e.message)
            }
        }
    }
}
