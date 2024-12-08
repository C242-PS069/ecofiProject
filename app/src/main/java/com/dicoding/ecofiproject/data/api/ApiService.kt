package com.dicoding.ecofiproject.data.api

import com.dicoding.ecofiproject.data.response.ArticleDetailResponse
import com.dicoding.ecofiproject.data.response.ArticlesResponse
import com.dicoding.ecofiproject.data.response.BannersResponse
import com.dicoding.ecofiproject.data.response.EditProfileRequest
import com.dicoding.ecofiproject.data.response.EditProfileResponse
import com.dicoding.ecofiproject.data.response.RegisterResponse
import com.dicoding.ecofiproject.data.response.LoginResponse
import com.dicoding.ecofiproject.data.response.PredictionResponse
import com.dicoding.ecofiproject.data.response.RecycleDetailsResponse
import com.dicoding.ecofiproject.data.response.ResetPasswordResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

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

    // Get all articles
    @GET("/api/articles")
    suspend fun getAllArticles(): Response<ArticlesResponse>

    // Get article by ID
    @GET("/api/articles/{id}")
    suspend fun getArticleById(
        @Path("id") id: Int
    ): Response<ArticleDetailResponse>

    // Get all banners
    @GET("/api/banners")
    suspend fun getAllBanners(): Response<BannersResponse>

    @Multipart
    @POST("/predict")
    fun predictImage(
        @Header("Authorization") token: String, // Menambahkan header Authorization
        @Part image: MultipartBody.Part // Gambar yang akan diprediksi
    ): Call<PredictionResponse>

    @GET("/api/recycles/{id}")
    fun getRecycleDetails(
        @Path("id") id: Int
    ): Call<RecycleDetailsResponse>
}



