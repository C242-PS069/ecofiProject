package com.dicoding.ecofiproject.ui.register

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dicoding.ecofiproject.ViewModelFactory
import com.dicoding.ecofiproject.databinding.ActivityRegisterBinding
import com.dicoding.ecofiproject.ui.login.LoginActivity
import androidx.appcompat.app.AlertDialog
import android.content.Intent
import android.widget.Toast
import com.dicoding.ecofiproject.utils.Result

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupAction() {
        // Menangani klik tombol register
        binding.registerButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            // Validasi input
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Panggil fungsi register dari ViewModel
            registerViewModel.register(name, email, password)
        }

        // Menangani live data dari ViewModel
        registerViewModel.registerResult.observe(this, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    // Menampilkan loading saat proses pendaftaran
                    showLoading(true)
                }

                is Result.Success -> {
                    // Menyembunyikan loading dan menampilkan pesan sukses
                    showLoading(false)
                    AlertDialog.Builder(this).apply {
                        setTitle("Selamat!")
                        setMessage(result.data) // Menampilkan pesan sukses dari result
                        setPositiveButton("OK") { _, _ ->
                            // Pindah ke LoginActivity setelah OK ditekan
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                }

                is Result.Error -> {
                    // Menyembunyikan loading dan menampilkan pesan error
                    showLoading(false)
                    AlertDialog.Builder(this).apply {
                        setTitle("Error")
                        setMessage("Maaf, register gagal. Ulang lagi ya.") // Pesan error
                        setPositiveButton("OK", null)
                        create()
                        show()
                    }
                }

                else -> {
                    // Penanganan jika result tidak sesuai dengan tipe yang diharapkan
                    showLoading(false)
                    AlertDialog.Builder(this).apply {
                        setTitle("Unexpected Error")
                        setMessage("Terjadi kesalahan yang tidak terduga. Silakan coba lagi.") // Pesan untuk kasus tak terduga
                        setPositiveButton("OK", null)
                        create()
                        show()
                    }
                }
            }
        })


        // Menangani klik "Already have an account?"
        binding.loginText.setOnClickListener {
            // Pindah ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
