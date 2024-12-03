package com.dicoding.ecofiproject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.ecofiproject.data.UserRepository
import com.dicoding.ecofiproject.di.Injection
import com.dicoding.ecofiproject.ui.login.LoginViewModel
import com.dicoding.ecofiproject.ui.register.RegisterViewModel

class ViewModelFactory private constructor(private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory() {
    // Konstruktor privat untuk menghindari pembuatan instance sembarangan.
    // Menyimpan UserRepository untuk di-passing ke ViewModel.

    @Suppress("UNCHECKED_CAST") // Menonaktifkan peringatan casting yang tidak aman.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Mengembalikan ViewModel sesuai dengan modelClass yang diberikan.
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                // Jika modelClass adalah LoginViewModel, buat LoginViewModel dan kirimkan UserRepository.
                LoginViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                // Jika modelClass adalah RegisterViewModel, buat RegisterViewModel dan kirimkan UserRepository.
                RegisterViewModel(userRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            // Jika modelClass tidak dikenali, lempar exception.
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).also {
                    instance = it
                }
            }
    }
}
