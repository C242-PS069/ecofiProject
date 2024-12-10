package com.dicoding.ecofiproject.ui.recommend

data class DataItem(
    // ID unik untuk setiap rekomendasi
    val title: String,         // Judul rekomendasi
    val description: String,   // Deskripsi rekomendasi
    val imageUrl: String,      // URL gambar
    val materials: List<String>  // Pas
)

