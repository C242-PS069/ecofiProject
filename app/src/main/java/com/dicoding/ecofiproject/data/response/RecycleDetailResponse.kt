package com.dicoding.ecofiproject.data.response

data class RecycleDetailsResponse(
    val status: String,
    val data: RecycleData
) {
    data class RecycleData(
        val id: Int,
        val title: String,
        val description: String,
        val materials: List<String>,
        val tools: Tools,
        val video: String
    ) {
        data class Tools(
            val title: String,
            val materialsTools: List<String>
        )
    }
}
