package com.dicoding.ecofiproject.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecofiproject.ViewModelFactory
import com.dicoding.ecofiproject.databinding.ActivityLoginBinding
import com.dicoding.ecofiproject.MainActivity
import com.dicoding.ecofiproject.ui.register.RegisterActivity
import android.content.SharedPreferences

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi SharedPreferences untuk menyimpan status login
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        setupView()
        setupAction()
    }

    private fun setupView() { 
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            showLoading(true)
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            // Cek jika email dan password kosong
            if (email.isEmpty() || password.isEmpty()) {
                showLoading(false)
                showErrorDialog("Email dan password tidak boleh kosong!")
                return@setOnClickListener
            }

            // Panggil fungsi login dari ViewModel
            loginViewModel.login(email, password) { isSuccess, message ->
                showLoading(false)

                if (isSuccess) {
                    // Simpan status login di SharedPreferences
                    saveLoginStatus(true)

                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!")
                        setMessage("Login berhasil. Selamat berkreasi!")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                    Log.d("LoginActivity", "Login successful: $message")
                } else {
                    showErrorDialog("Login gagal. Pesan error: $message")
                    Log.e("LoginActivity", "Login failed: $message")
                }
            }
        }

        binding.registerText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    // Fungsi untuk menampilkan dialog error
    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Oops!")
            setMessage(message)
            setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            create()
            show()
        }
    }

    // Fungsi untuk menyimpan status login di SharedPreferences
    private fun saveLoginStatus(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", isLoggedIn)
        editor.apply()
        Log.d("LoginActivity", "Login status saved: isLoggedIn=$isLoggedIn")
    }
}
