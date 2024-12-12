package com.dicoding.ecofiproject.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PredictionResponse(
    @SerializedName("status") val status: String,           // Status permintaan
    @SerializedName("message") val message: String,         // Pesan error atau informasi lainnya
    @SerializedName("data") val data: List<DataItem>,      // Data rekomendasi yang dikirim dari API
    @SerializedName("predict") val predict: Predict         // Hasil prediksi dari gambar
)
@Parcelize
data class DataItem(
    @SerializedName("id") val id: Int,                     // ID item
    @SerializedName("description") val description: String, // Deskripsi produk
    @SerializedName("image") val image: String,             // URL gambar produk
    @SerializedName("materials") val materials: List<String>,// List material terkait
    @SerializedName("title") val title: String,
    @SerializedName("video")val video: String?// Judul produk
) : Parcelable

data class Predict(
    @SerializedName("confident") val confident: String,     // Tingkat kepercayaan prediksi
    @SerializedName("label") val label: String              // Label material yang diprediksi
)

