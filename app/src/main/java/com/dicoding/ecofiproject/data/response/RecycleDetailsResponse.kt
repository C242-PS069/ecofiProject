package com.dicoding.ecofiproject.data.response

import com.google.gson.annotations.SerializedName

data class RecycleDetailsResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: RecycleData
)

data class RecycleData(
    @SerializedName("id")
    val id: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("makes")
    val makes: Makes,

    @SerializedName("title")
    val title: String,

    @SerializedName("materials")
    val materials: List<String>,

    @SerializedName("tools")
    val tools: Tools,

    @SerializedName("video")
    val video: String
)

data class Makes(
    @SerializedName("step")
    val step: List<String>,

    @SerializedName("title")
    val title: String
)

data class Tools(
    @SerializedName("materials-tools")
    val materialsTools: List<String>,

    @SerializedName("title")
    val title: String
)
