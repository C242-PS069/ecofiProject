package com.dicoding.ecofiproject.data.api

import com.dicoding.ecofiproject.data.response.RegisterResponse
import com.dicoding.ecofiproject.data.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("/register")
    suspend fun register(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>
}

