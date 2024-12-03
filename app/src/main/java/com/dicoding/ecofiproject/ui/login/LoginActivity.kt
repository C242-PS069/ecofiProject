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

// LoginActivity untuk menangani tampilan dan logika login pengguna
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding // Binding untuk layout activity_login.xml
    private val loginViewModel: LoginViewModel by viewModels { // Menggunakan ViewModel untuk login
        ViewModelFactory.getInstance(this) // Menyediakan instance ViewModel menggunakan ViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater) // Menginflate layout
        setContentView(binding.root) // Menetapkan root layout sebagai tampilan activity

        setupView() // Menyiapkan tampilan
        setupAction() // Menyiapkan aksi tombol dan klik
    }

    // Fungsi untuk setup tampilan activity
    private fun setupView() {
        supportActionBar?.hide() // Menyembunyikan ActionBar (untuk tampilan full screen)
    }

    // Fungsi untuk menangani aksi klik tombol dan tautan
    private fun setupAction() {
        // Menangani klik tombol login
        binding.loginButton.setOnClickListener {
            showLoading(true) // Menampilkan loading indicator
            val email = binding.edLoginEmail.text.toString() // Mengambil email dari input
            val password = binding.edLoginPassword.text.toString() // Mengambil password dari input

            // Memanggil fungsi login dari ViewModel dan memberikan callback untuk menangani hasil
            loginViewModel.login(email, password) { isSuccess, message ->
                showLoading(false) // Menyembunyikan loading indicator setelah response diterima
                if (isSuccess) {
                    // Jika login berhasil, tampilkan dialog konfirmasi dan pindah ke MainActivity
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!") // Judul dialog
                        setMessage("Login berhasil. Selamat berkreasi!") // Pesan dialog
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            // Mengatur flag untuk clear task dan membuka MainActivity
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent) // Menjalankan MainActivity
                            finish() // Menutup LoginActivity
                        }
                        create()
                        show()
                    }
                    Log.d("LoginActivity", "Login successful: $message") // Menampilkan log sukses
                } else {
                    // Ketika login gagal, tampilkan pesan error
                    AlertDialog.Builder(this).apply {
                        setTitle("Oops!")
                        setMessage("Login gagal. Silakan coba lagi. Pesan error: $message")
                        setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        create()
                        show()
                    }
                    Log.e("LoginActivity", "Login failed: $message") // Menampilkan log error
                }
            }
        }

        // Menangani klik teks "Don’t have an account? Sign up"
        binding.registerText.setOnClickListener {
            // Pindah ke RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent) // Menjalankan RegisterActivity
            finish() // Menutup LoginActivity
        }
    }

    // Fungsi untuk menampilkan atau menyembunyikan loading indicator
    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
