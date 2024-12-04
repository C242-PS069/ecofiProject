package com.dicoding.ecofiproject.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import com.dicoding.ecofiproject.MainActivity
import com.dicoding.ecofiproject.data.UserRepository
import com.dicoding.ecofiproject.data.pref.UserPreference
import com.dicoding.ecofiproject.databinding.FragmentProfileBinding
import com.dicoding.ecofiproject.ui.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi UserRepository
        val userPreference = UserPreference.getInstance(requireContext().dataStore)
        userRepository = UserRepository.getInstance(userPreference)

        // Inisialisasi switch dengan status dari preferensi
        val sharedPreferences = (activity as MainActivity).getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        binding.switchTheme.isChecked = isDarkMode

        // Listener untuk switch theme
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            // Simpan preferensi
            (activity as MainActivity).saveThemePreference(isChecked)
            // Update tema tanpa restart aktivitas
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Tambahkan listener untuk tombol logout
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        // Lakukan operasi logout dalam Coroutine
        CoroutineScope(Dispatchers.Main).launch {
            userRepository.logout()

            // Redirect ke Login Activity
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
