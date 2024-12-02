package com.dicoding.ecofiproject

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.dicoding.ecofiproject.databinding.ActivityMainBinding
import com.dicoding.ecofiproject.ui.home.HomeFragment
import com.dicoding.ecofiproject.ui.profile.ProfileFragment
import com.dicoding.ecofiproject.ui.scan.ScanFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    private val SELECTED_ITEM_KEY = "selected_item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        // Setel tema berdasarkan preferensi yang tersimpan
        setThemeFromPreferences()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tampilkan fragment default saat aplikasi dibuka atau kembalikan fragment yang disimpan
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        } else {
            when (savedInstanceState.getInt(SELECTED_ITEM_KEY)) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_scan -> loadFragment(ScanFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
                else -> loadFragment(HomeFragment())
            }
        }

        // Listener untuk navigasi antar fragment
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_scan -> loadFragment(ScanFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        return true
    }

    // Fungsi untuk menyetel tema berdasarkan preferensi yang tersimpan
    private fun setThemeFromPreferences() {
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    // Fungsi untuk menyimpan preferensi tema
    fun saveThemePreference(isDarkMode: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean("dark_mode", isDarkMode)
            apply()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Simpan item yang dipilih saat ini
        outState.putInt(SELECTED_ITEM_KEY, binding.bottomNavigationView.selectedItemId)
    }
}
