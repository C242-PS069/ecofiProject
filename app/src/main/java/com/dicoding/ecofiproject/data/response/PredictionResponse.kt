package com.dicoding.ecofiproject.data.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<DataItem>,
    @SerializedName("predict") val predict: Predict
)

data class DataItem(
    @SerializedName("id") val id: Int,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("materials") val materials: List<String>,
    @SerializedName("title") val title: String
)

data class Predict(
    @SerializedName("confident") val confident: String,
    @SerializedName("label") val label: String
)
