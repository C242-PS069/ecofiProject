
package com.dicoding.ecofiproject.data.response

data class PredictResponse(
    val status: String,
    val message: String,
    val data: List<PredictionData>,
    val predict: PredictionDetails
)

data class PredictionData(
    val id: Int,
    val description: String,
    val image: String,
    val materials: List<String>,
    val title: String
)

data class PredictionDetails(
    val confident: String,
    val label: String
)