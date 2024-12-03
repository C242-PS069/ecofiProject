package com.dicoding.ecofiproject.ui.login

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

        setupView()
        setupAction()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupAction() {
        // Menangani klik tombol login
        binding.loginButton.setOnClickListener {
            showLoading(true)
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            loginViewModel.login(email, password) { isSuccess, message ->
                showLoading(false)
                if (isSuccess) {
                    // Jika login berhasil, tampilkan dialog dan pindah ke MainActivity
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
                    // Jika login gagal, tampilkan pesan error
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

        // Menangani klik teks "Don’t have an account? Sign up"
        binding.registerText.setOnClickListener {
            // Pindah ke RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
