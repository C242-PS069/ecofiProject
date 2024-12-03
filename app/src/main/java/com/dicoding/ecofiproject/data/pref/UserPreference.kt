package com.dicoding.ecofiproject.data.pref

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Ekstensi untuk menyediakan instance DataStore dengan nama "session"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

// Kelas UserPreference untuk menangani penyimpanan preferensi pengguna menggunakan DataStore
class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    // Fungsi untuk menyimpan sesi pengguna
    suspend fun saveSession(user: UserModel) {
        Log.d(TAG, "saveSession: Saving user session: $user") // Menampilkan log untuk proses penyimpanan sesi
        dataStore.edit { preferences ->
            // Menyimpan nilai-nilai dari UserModel ke dalam DataStore
            preferences[NAME_KEY] = user.name
            preferences[EMAIL_KEY] = user.email
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = true // Mengindikasikan bahwa pengguna sudah login
            Log.d(TAG, "saveSession: User session saved: $preferences") // Menampilkan log setelah sesi disimpan
        }
    }

    // Fungsi untuk mengambil sesi pengguna (mengembalikan Flow<UserModel>)
    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            // Mengambil nilai-nilai dari DataStore dan membentuk UserModel
            UserModel(
                name = preferences[NAME_KEY] ?: "",  // Mengambil nilai string untuk nama, jika tidak ada default-nya ""
                email = preferences[EMAIL_KEY] ?: "", // Mengambil nilai string untuk email, jika tidak ada default-nya ""
                token = preferences[TOKEN_KEY] ?: "", // Mengambil nilai string untuk token, jika tidak ada default-nya ""
                isLogin = preferences[IS_LOGIN_KEY] ?: false // Mengambil nilai Boolean untuk status login, jika tidak ada default-nya false
            )
        }
    }

    // Fungsi untuk melakukan logout (menghapus semua preferensi sesi)
    suspend fun logout() {
        dataStore.edit { preferences ->
            // Menghapus semua data yang tersimpan di DataStore
            preferences.clear()
        }
    }

    companion object {
        // Variabel untuk menyimpan instance singleton dari UserPreference
        @Volatile
        private var INSTANCE: UserPreference? = null

        // Kunci preferensi untuk menyimpan data pengguna
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        // Fungsi untuk mendapatkan instance dari UserPreference (singleton)
        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                // Membuat instance jika belum ada, dan menyimpannya di INSTANCE
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
