package com.dicoding.ecofiproject.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.ecofiproject.data.UserRepository
import com.dicoding.ecofiproject.data.pref.UserModel
import kotlinx.coroutines.launch

// ViewModel untuk login, yang mengelola data dan logika terkait login pengguna
class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    // Fungsi untuk menyimpan sesi pengguna ke preference
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user) // Menyimpan sesi menggunakan repository
            Log.d("UserRepository", "Saving session with token: ${user.token}") // Menampilkan log untuk debugging
        }
    }

    // Fungsi login yang menerima email dan password, dan memberikan callback untuk menangani hasil login
    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val token = loginResponse?.data?.token
                    if (!token.isNullOrEmpty()) {
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
