package com.dicoding.ecofiproject.ui.profile

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecofiproject.data.api.ApiConfig
import com.dicoding.ecofiproject.data.response.EditProfileRequest
import com.dicoding.ecofiproject.data.response.EditProfileResponse
import com.dicoding.ecofiproject.databinding.ActivityEditProfileBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Response
import com.dicoding.ecofiproject.data.pref.UserPreference
import com.dicoding.ecofiproject.data.pref.dataStore

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi UserPreference
        userPreference = UserPreference.getInstance(applicationContext.dataStore)

        setupToolbar()
        setupActions()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun saveProfile() {
        val name = binding.etEditName.text.toString()
        val email = binding.etEditEmail.text.toString()
        val oldPassword = binding.etOldPassword.text.toString()
        val newPassword = binding.etNewPassword.text.toString()

        Log.d("EditProfileActivity", "Save profile triggered with name: $name, email: $email")

        if (validateInputs(name, email, oldPassword, newPassword)) {
            val request = EditProfileRequest(name, oldPassword, newPassword, email)
            Log.d("EditProfileActivity", "Request object created: $request")

            lifecycleScope.launch {
                userPreference.getSession().collect { user ->
                    val token = user.token

                    if (token.isNullOrEmpty()) {
                        Log.e("EditProfileActivity", "Token is null or empty")
                        showToast("Token tidak ditemukan!")
                        return@collect
                    }

                    Log.d("EditProfileActivity", "Using token: $token")

                    val response: Response<EditProfileResponse> =
                        ApiConfig.getApiService(token).editProfile(request)

                    if (response.isSuccessful) {
                        response.body()?.let {
                            showToast(it.message)
                        }
                    } else {
                        showToast("Error: ${response.errorBody()?.string()}")
                    }
                }
            }
        } else {
            Log.w("EditProfileActivity", "Invalid input: One or more fields are empty.")
            showToast("Silakan isi semua field")
        }
    }

    private fun validateInputs(
        name: String,
        email: String,
        oldPassword: String,
        newPassword: String
    ): Boolean {
        return name.isNotEmpty() && email.isNotEmpty() && oldPassword.isNotEmpty() && newPassword.isNotEmpty()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupActions() {
        binding.btnSaveProfile.setOnClickListener { saveProfile() }

    }
}
