package com.dicoding.ecofiproject.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.ecofiproject.data.UserRepository
import com.dicoding.ecofiproject.utils.Result
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<Result<String>>()
    val registerResult: LiveData<Result<String>> = _registerResult

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _registerResult.value = Result.Loading
            try {
                val result = userRepository.register(name, email, password)

                if (result.isSuccessful) {
                    _registerResult.value = Result.Success(data = "Registration successful")
                    Log.d("Register", "Registration successful for $email")  // Menambahkan log sukses
                } else {
                    _registerResult.value = Result.Error(result.message())
                    Log.e("Register", "Registration failed: ${result.message()}")  // Menambahkan log error
                }
            } catch (e: Exception) {
                _registerResult.value = Result.Error(e.message ?: "Unknown error")
                Log.e("Register", "Error during registration: ${e.message ?: "Unknown error"}")  // Menambahkan log untuk error exception
            }

        }
    }
}
