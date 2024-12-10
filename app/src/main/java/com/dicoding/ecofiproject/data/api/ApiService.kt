package com.dicoding.ecofiproject.data.api

import com.dicoding.ecofiproject.data.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

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

    @POST("reset-password")
    suspend fun resetPassword(
        @Body email: Map<String, String>
    ): Response<ResetPasswordResponse>

    @POST("edit-profile")
    suspend fun editProfile(
        @Body editProfileRequest: EditProfileRequest
    ): Response<EditProfileResponse>

    @GET("/api/articles")
    suspend fun getAllArticles(): Response<ArticlesResponse>

    @GET("/api/articles/{id}")
    suspend fun getArticleById(@Path("id") id: Int): Response<ArticleDetailResponse>

    @GET("/api/banners")
    suspend fun getAllBanners(): Response<BannersResponse>

    @Multipart
    @POST("/predict")
    fun predictImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): Call<PredictionResponse>


    @GET("/api/recycles/{id}")
    fun getRecycleDetails(@Path("id") id: Int): Call<RecycleDetailsResponse>
}

//Test