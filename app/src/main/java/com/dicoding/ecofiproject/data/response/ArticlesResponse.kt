package com.dicoding.ecofiproject.data.response

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<Article>
) {
    data class Article(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("image") val image: String
    )
}
