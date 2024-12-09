package com.dicoding.ecofiproject.data.response

data class PredictionResponse(
    val status: String,
    val predict: PredictionData
)

data class PredictionData(
    val confident: String,
    val label: String,
    val title: String?,
    val description: String?
)
