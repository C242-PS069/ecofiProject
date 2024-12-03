package com.dicoding.ecofiproject.ui.register

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
                    _registerResult.value = Result.Success("Registration successful")
                } else {
                    _registerResult.value = Result.Error(result.message())
                }
            } catch (e: Exception) {
                _registerResult.value = Result.Error(e.message ?: "Unknown error")
            }
        }
    }
}
