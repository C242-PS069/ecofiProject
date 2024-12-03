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
            repository.saveSession(user)
            Log.d("UserRepository", "Saving session with token: ${user.token}")
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
                        Log.d("LoginViewModel", "Login successful: Token=$token")
                        callback(true, token)
                    } else {
                        Log.e("LoginViewModel", "Login failed: Token is null or empty")
                        callback(false, null)
                    }
                } else {
                    Log.e("LoginViewModel", "Login failed: ${response.errorBody()?.string()}")
                    callback(false, null)
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Login error: ${e.message}", e)
                callback(false, null)
            }
        }
    }
}
