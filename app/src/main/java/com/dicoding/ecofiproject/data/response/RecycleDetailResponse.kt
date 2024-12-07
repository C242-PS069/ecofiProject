// RecycleDetailsResponse.kt
package com.dicoding.ecofiproject.data.response

data class RecycleDetailsResponse(
    val status: String,
    val message: String,
    val data: RecycleDetailsData
)

data class RecycleDetailsData(
    val id: Int,
    val description: String,
    val makes: Makes,
    val title: String,
    val materials: List<String>,
    val tools: Tools,
    val video: String
)

data class Makes(
    val step: List<String>,
    val title: String
)

data class Tools(
    val `materials-tools`: List<String>,
    val title: String
)