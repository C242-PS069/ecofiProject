package com.dicoding.ecofiproject.di

import android.content.Context
import com.dicoding.ecofiproject.data.UserRepository
import com.dicoding.ecofiproject.data.pref.UserPreference
import com.dicoding.ecofiproject.data.pref.dataStore

object Injection {

    fun provideRepository(context: Context): UserRepository {
        val dataStore = context.dataStore
        val userPreference = UserPreference.getInstance(dataStore)
        return UserRepository.getInstance(userPreference)
    }
}
