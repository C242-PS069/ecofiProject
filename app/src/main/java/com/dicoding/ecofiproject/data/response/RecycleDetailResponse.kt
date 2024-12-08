// RecycleDetailsResponse.kt
package com.dicoding.ecofiproject.data.response

data class RecycleDetailsResponse(
    val status: String,
    val message: String,
    val data: RecycleDetails
)

data class RecycleDetails(
    val id: Int,
    val description: String,
    val makes: StepDetails,
    val title: String,
    val materials: List<String>,
    val tools: ToolsDetails,
    val video: String
)

data class StepDetails(
    val title: String,
    val step: List<String>
)

data class ToolsDetails(
    val title: String,
    val materialsTools: List<String>
)
