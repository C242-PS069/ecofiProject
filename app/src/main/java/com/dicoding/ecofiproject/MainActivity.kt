package com.dicoding.ecofiproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dicoding.ecofiproject.data.UserRepository
import com.dicoding.ecofiproject.data.pref.UserPreference
import com.dicoding.ecofiproject.data.pref.dataStore
import com.dicoding.ecofiproject.databinding.ActivityMainBinding
import com.dicoding.ecofiproject.ui.home.HomeFragment
import com.dicoding.ecofiproject.ui.login.LoginActivity
import com.dicoding.ecofiproject.ui.profile.ProfileFragment
import com.dicoding.ecofiproject.ui.scan.ScanFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userRepository: UserRepository

    private val SELECTED_ITEM_KEY = "selected_item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        setThemeFromPreferences()

        val userPreference = UserPreference.getInstance(applicationContext.dataStore)
        userRepository = UserRepository.getInstance(userPreference)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            loadFragment(HomeFragment()) // Menampilkan HomeFragment jika sudah login
        } else {
            when (savedInstanceState.getInt(SELECTED_ITEM_KEY)) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_scan -> loadFragment(ScanFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
                else -> loadFragment(HomeFragment())
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_scan -> loadFragment(ScanFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
                else -> false
            }
        }
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launchWhenStarted {
            userRepository.getSession().collect { user ->
                if (!user.isLogin) {
                    // Pengguna belum login, arahkan ke LoginActivity hanya jika belum berada di LoginActivity
                    if (javaClass != LoginActivity::class.java) {
                        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                        finish()  // Tutup MainActivity agar tidak bisa kembali ke sini
                    }
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        return true
    }

    private fun setThemeFromPreferences() {
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun saveThemePreference(isDarkMode: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean("dark_mode", isDarkMode)
            apply()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_ITEM_KEY, binding.bottomNavigationView.selectedItemId)
    }
}
