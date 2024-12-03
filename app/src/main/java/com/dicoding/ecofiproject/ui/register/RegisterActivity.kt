package com.dicoding.ecofiproject.ui.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dicoding.ecofiproject.ViewModelFactory
import com.dicoding.ecofiproject.databinding.ActivityRegisterBinding
import com.dicoding.ecofiproject.ui.login.LoginActivity
import androidx.appcompat.app.AlertDialog
import android.content.Intent
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

            // Panggil fungsi register dari ViewModel
            registerViewModel.register(name, email, password)
        }

        // Menangani live data dari ViewModel
        registerViewModel.registerResult.observe(this, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    // Tampilkan loading
                    showLoading(true)
                }
                is Result.Success -> {
                    // Pendaftaran berhasil
                    showLoading(false)
                    AlertDialog.Builder(this).apply {
                        setTitle("Selamat!")
                        setMessage(result.data)
                        setPositiveButton("OK") { _, _ ->
                            // Kembali ke LoginActivity setelah pendaftaran berhasil
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                }
                is Result.Error -> {
                    // Tampilkan pesan error
                    showLoading(false)
                    AlertDialog.Builder(this).apply {
                        setTitle("Error")
                        setMessage(result.message)
                        setPositiveButton("OK", null)
                        create()
                        show()
                    }
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
