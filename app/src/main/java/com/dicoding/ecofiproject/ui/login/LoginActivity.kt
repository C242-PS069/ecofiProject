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

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cek status login saat activity dibuat
        checkLoginStatus()

        setupView()
        setupAction()
    }

    private fun checkLoginStatus() {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
        if (isLoggedIn) {
            // Jika pengguna sudah login, arahkan ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() // Tutup LoginActivity
        }
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            showLoading(true)
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            loginViewModel.login(email, password) { isSuccess, message ->
                showLoading(false)
                if (isSuccess) {
                    saveLoginStatus(true) // Simpan status login

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
                    AlertDialog.Builder(this).apply {
                        setTitle("Oops!")
                        setMessage("Login gagal. Silakan coba lagi. Pesan error: $message")
                        setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        create()
                        show()
                    }
                    Log.e("LoginActivity", "Login failed: $message")
                }
            }
        }

        binding.registerText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveLoginStatus(isLoggedIn: Boolean) {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", isLoggedIn)
        editor.apply()
        Log.d("LoginActivity", "Login status saved: isLoggedIn=$isLoggedIn")
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
