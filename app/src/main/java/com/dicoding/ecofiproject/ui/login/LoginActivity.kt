package com.dicoding.ecofiproject.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
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

        setupView()
        setupActions()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupActions() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                showErrorDialog("Email dan password tidak boleh kosong!")
                return@setOnClickListener
            }

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

        binding.registerText.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.eyeIcon.setOnClickListener {
            togglePasswordVisibility()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible

        val passwordField: EditText = binding.edLoginPassword
        val eyeIcon: ImageView = binding.eyeIcon

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
}
