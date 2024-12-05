package com.dicoding.ecofiproject.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecofiproject.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengatur toolbar sebagai action bar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Menangani aksi tombol kembali di toolbar
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Aksi untuk tombol save profile
        binding.btnSaveProfile.setOnClickListener {
            // Lakukan aksi penyimpanan profil di sini
            saveProfile()
        }
    }

    private fun saveProfile() {
        // Ambil data dari input
        val name = binding.etEditName.text.toString()
        val email = binding.etEditEmail.text.toString()
        val oldPassword = binding.etOldPassword.text.toString()
        val newPassword = binding.etNewPassword.text.toString()

        // Lakukan validasi data dan kirim ke server atau simpan secara lokal
        if (name.isNotEmpty() && email.isNotEmpty()) {
            // Jika validasi berhasil, lanjutkan penyimpanan data
            // Misalnya, mengirim data ke server atau menyimpannya dalam database
            // Implementasi sesuai dengan kebutuhan aplikasi

            // Contoh: Menampilkan pesan bahwa profil telah disimpan (untuk testing)
            // Toast.makeText(this, "Profile saved successfully", Toast.LENGTH_SHORT).show()
        } else {
            // Tampilkan pesan jika ada field yang kosong
            // Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
