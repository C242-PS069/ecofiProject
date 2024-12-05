package com.dicoding.ecofiproject.data.api

import com.dicoding.ecofiproject.data.response.EditProfileRequest
import com.dicoding.ecofiproject.data.response.EditProfileResponse
import com.dicoding.ecofiproject.data.response.RegisterResponse
import com.dicoding.ecofiproject.data.response.LoginResponse
import com.dicoding.ecofiproject.data.response.ResetPasswordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    // Method untuk melakukan pendaftaran pengguna
    @FormUrlEncoded
    @POST("/register")
    suspend fun register(
        @Field("username") name: String, // Field untuk mengirimkan nama pengguna
        @Field("email") email: String, // Field untuk mengirimkan email pengguna
        @Field("password") password: String // Field untuk mengirimkan password pengguna
    ): Response<RegisterResponse> // Mengembalikan response dari server dalam bentuk RegisterResponse

    // Method untuk melakukan login pengguna
    @FormUrlEncoded
    @POST("/login")
    suspend fun login(
        @Field("email") email: String, // Field untuk mengirimkan email pengguna
        @Field("password") password: String // Field untuk mengirimkan password pengguna
    ): Response<LoginResponse> // Mengembalikan response dari server dalam bentuk LoginResponse

    @POST("reset-password")
    suspend fun resetPassword(
        @Body email: Map<String, String>
    ): Response<ResetPasswordResponse>

    @POST("edit-profile")
    suspend fun editProfile(
        @Body editProfileRequest: EditProfileRequest
    ): Response<EditProfileResponse>
}

