package com.dicoding.ecofiproject.data.response

data class BannersResponse(
    val status: String,
    val message: String,
    val data: List<Banner>
) {
    data class Banner(
        val id: Int,
        val image: String
    )
}
