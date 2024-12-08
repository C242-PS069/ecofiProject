package com.dicoding.ecofiproject.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private const val BASE_URL = "https://ecofy-373030918770.asia-southeast2.run.app"

    // Fungsi untuk mendapatkan ApiService dengan token optional
    fun getApiService(token: String? = null): ApiService {
        // Logging interceptor untuk melihat request dan response
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        // Builder OkHttpClient untuk menambahkan interceptor
        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Menambahkan logging

        // Jika token diberikan, kita tambahkan interceptor untuk Authorization header
        token?.let {
            val authInterceptor = Interceptor { chain ->
                val request = chain.request()
                // Menambahkan header Authorization dengan token Bearer
                val newRequest = request.newBuilder()
                    .addHeader("Authorization", "Bearer $it")
                    .addHeader("Content-Type", "application/json") // Tambahkan Content-Type jika diperlukan
                    .build()
                chain.proceed(newRequest)
            }
            clientBuilder.addInterceptor(authInterceptor) // Menambahkan auth interceptor
        }

        // Membangun OkHttpClient dengan interceptor
        val client = clientBuilder.build()

        // Membangun Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // URL dasar API
            .addConverterFactory(GsonConverterFactory.create()) // Converter Gson untuk deserialisasi JSON
            .client(client) // Menambahkan OkHttpClient yang telah dikonfigurasi
            .build()

        // Mengembalikan instance ApiService
        return retrofit.create(ApiService::class.java)
    }
}
