package com.dicoding.ecofiproject.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecofiproject.R
import com.dicoding.ecofiproject.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registerButton: Button
    private lateinit var loginText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register) // Pastikan layout sudah benar

        // Inisialisasi view
        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        registerButton = findViewById(R.id.registerButton)
        loginText = findViewById(R.id.loginText)

        // Menangani klik tombol Register
        registerButton.setOnClickListener {
            val name = nameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Panggil fungsi untuk menangani registrasi (misalnya API atau penyimpanan lokal)
                // Fungsi ini hanya berupa contoh, sesuaikan dengan cara pendaftaran yang digunakan

                // Jika berhasil registrasi
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()

                // Setelah registrasi sukses, pindah ke LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish() // Menutup RegisterActivity
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Menangani klik teks "Already have an account? Login"
        loginText.setOnClickListener {
            // Pindah ke LoginActivity ketika teks Login dipencet
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
