package com.dicoding.ecofiproject.data.response

import com.google.gson.annotations.SerializedName

data class ArticleDetailResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: ArticleDetail
) {
    data class ArticleDetail(
        @SerializedName("id")
        val id: Int,

        @SerializedName("title")
        val title: String,

        @SerializedName("content")
        val content: List<ContentSection>,

        @SerializedName("video")
        val video: String?
    ) {
        data class ContentSection(
            @SerializedName("section")
            val section: String,

            @SerializedName("text")
            val text: String
        )
    }
}
