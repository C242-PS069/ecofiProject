package com.dicoding.ecofiproject.data.response

data class PredictionResponse(
    val status: String,
    val message: String,
    val data: List<PredictionData>, // List dari PredictionData
    val predict: Prediction // Prediksi yang berisi label dan confident
)

data class PredictionData(
    val id: Int, // ID dari data
    val description: String, // Deskripsi produk atau hasil prediksi
    val image: String, // URL gambar terkait produk/hasil
    val materials: List<String>, // Daftar bahan yang diperlukan
    val title: String // Judul produk/hasil
)

data class Prediction(
    val confident: Float, // Tipe data Float atau Double jika confident adalah angka
    val label: String // Label hasil prediksi
)
