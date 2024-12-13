package com.dicoding.ecofiproject.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import android.view.View
import com.dicoding.ecofiproject.R
import com.dicoding.ecofiproject.ViewModelFactory
import com.dicoding.ecofiproject.databinding.ActivityRegisterBinding
import com.dicoding.ecofiproject.ui.login.LoginActivity
import com.dicoding.ecofiproject.utils.Result

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup view dan action
        setupView()
        setupAction()

        // Terapkan animasi
        applyAnimations()
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

            // Validasi panjang password
            if (password.length < 8) {
                Toast.makeText(this, "Password harus memiliki minimal 8 karakter", Toast.LENGTH_SHORT).show()
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

                    // Simpan data user ke SharedPreferences
                    val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("username", binding.nameInput.text.toString())
                    editor.putString("email", binding.emailInput.text.toString())
                    editor.apply()

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

        // Menangani klik ikon visibility
        binding.eyeIcon.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            updatePasswordVisibility()
        }
    }

    private fun updatePasswordVisibility() {
        if (isPasswordVisible) {
            binding.passwordInput.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.eyeIcon.setImageResource(R.drawable.ic_visibility_on)
        } else {
            binding.passwordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.eyeIcon.setImageResource(R.drawable.ic_visibility_off)
        }
        // Set cursor di akhir teks setelah mengubah tipe input
        binding.passwordInput.setSelection(binding.passwordInput.text.length)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun applyAnimations() {
        // Logo bergerak kanan kiri
        val logoImage = binding.logoImage
        val logoAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.move_logo)
        logoImage.startAnimation(logoAnim)

        // Tombol register dengan animasi scale
        val registerButton = binding.registerButton
        val scaleAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.scale_button)
        registerButton.startAnimation(scaleAnim)
    }
}
