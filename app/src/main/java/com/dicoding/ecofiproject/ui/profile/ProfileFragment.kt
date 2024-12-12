package com.dicoding.ecofiproject.ui.profile

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dicoding.ecofiproject.MainActivity
import com.dicoding.ecofiproject.R
import com.dicoding.ecofiproject.ui.pro.ProActivity
import com.dicoding.ecofiproject.data.UserRepository
import com.dicoding.ecofiproject.data.pref.UserPreference
import com.dicoding.ecofiproject.databinding.FragmentProfileBinding
import com.dicoding.ecofiproject.ui.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class ProfileFragment : Fragment() {

    private val profileImageUrl = "https://storage.googleapis.com/user-profile-ecofy/profile.png"

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var userRepository: UserRepository

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi UserRepository
        val userPreference = UserPreference.getInstance(requireContext().dataStore)
        userRepository = UserRepository.getInstance(userPreference)

        // Mengambil data dari SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "Guest")
        val email = sharedPreferences.getString("email", "example@example.com")

        // Menampilkan nama dan email di UI
        binding.tvUsername.text = username
        binding.tvEmail.text = email

        // Menggunakan Glide untuk memuat gambar profil
        Glide.with(this)
            .load(profileImageUrl)
            .into(binding.ivProfilePicture)

        // Terapkan animasi bounce pada ImageView
        val bounceAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)
        binding.imageView.startAnimation(bounceAnimation)

        // Listener untuk imageView
        binding.imageView.setOnClickListener {
            val intent = Intent(requireContext(), ProActivity::class.java)
            startActivity(intent)
        }

        // Inisialisasi switch tema
        val themePreferences = (activity as MainActivity).getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        val isDarkMode = themePreferences.getBoolean("dark_mode", false)
        binding.switchTheme.isChecked = isDarkMode

        // Listener untuk switch tema
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            (activity as MainActivity).saveThemePreference(isChecked)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Inisialisasi switch bahasa
        val languagePreferences = requireActivity().getSharedPreferences("language_prefs", Context.MODE_PRIVATE)
        val isIndonesian = languagePreferences.getBoolean("language_indonesia", true)
        binding.switchLanguage.isChecked = isIndonesian

        // Listener untuk switch bahasa
        binding.switchLanguage.setOnCheckedChangeListener { _, isChecked ->
            languagePreferences.edit().putBoolean("language_indonesia", isChecked).apply()
            if (isChecked) {
                setLocale("id") // Bahasa Indonesia
            } else {
                setLocale("en") // Bahasa Inggris
            }
        }

        // Listener untuk tombol Edit Profile
        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }

        // Listener untuk tombol logout
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)

        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }

    private fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            userRepository.logout()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
