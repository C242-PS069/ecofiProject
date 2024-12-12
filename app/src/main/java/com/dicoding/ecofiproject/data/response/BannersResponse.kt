package com.dicoding.ecofiproject.data.response

import com.google.gson.annotations.SerializedName

data class BannersResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<Banner>
) {
    data class Banner(
        @SerializedName("id")
        val id: Int,

        @SerializedName("image")
        val image: String
    )
}
