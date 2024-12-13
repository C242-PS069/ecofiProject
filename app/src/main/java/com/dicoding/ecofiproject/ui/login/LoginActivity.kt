package com.dicoding.ecofiproject.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecofiproject.MainActivity
import com.dicoding.ecofiproject.R
import com.dicoding.ecofiproject.ViewModelFactory
import com.dicoding.ecofiproject.databinding.ActivityLoginBinding
import com.dicoding.ecofiproject.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup view dan action
        setupView()
        setupActions()

        // Terapkan animasi
        applyAnimations()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupActions() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            // Check if email or password is empty
            if (email.isEmpty() || password.isEmpty()) {
                showErrorDialog("Email dan password tidak boleh kosong!")
                return@setOnClickListener
            }

            // Check if password length is less than 8 characters
            if (password.length < 8) {
                showErrorDialog("Password harus memiliki minimal 8 karakter!")
                return@setOnClickListener
            }

            // Proceed with login
            showLoading(true)
            loginViewModel.login(email, password) { isSuccess, message ->
                showLoading(false)
                if (isSuccess) {
                    onLoginSuccess()
                } else {
                    showErrorDialog("Login gagal. Pesan error: $message")
                }
            }
        }

        // Redirect to RegisterActivity when the "Register" text is clicked
        binding.registerText.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        // Toggle password visibility when the eye icon is clicked
        binding.eyeIcon.setOnClickListener {
            togglePasswordVisibility()
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible

        val passwordField = binding.edLoginPassword
        val eyeIcon = binding.eyeIcon

        if (isPasswordVisible) {
            passwordField.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            eyeIcon.setImageResource(R.drawable.ic_visibility_on)
        } else {
            passwordField.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            eyeIcon.setImageResource(R.drawable.ic_visibility_off)
        }

        passwordField.setSelection(passwordField.text.length)
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Oops!")
            setMessage(message)
            setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            create()
            show()
        }
    }

    private fun onLoginSuccess() {
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
    }

    private fun applyAnimations() {
        // Logo bergerak kanan kiri
        val logoImage: ImageView = binding.logoImage
        val logoAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.move_logo)
        logoImage.startAnimation(logoAnim)

        // Teks "Welcome" fade-in
        val welcomeText: TextView = binding.welcomeText
        val welcomeAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        welcomeText.startAnimation(welcomeAnim)

        // Teks "Quotes" fade-in
        val subtitleText: TextView = binding.subtitleText
        val subtitleAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        subtitleText.startAnimation(subtitleAnim)

        // Tombol login dengan animasi scale
        val loginButton: Button = binding.loginButton
        val scaleAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.scale_button)
        loginButton.startAnimation(scaleAnim)
    }
}
