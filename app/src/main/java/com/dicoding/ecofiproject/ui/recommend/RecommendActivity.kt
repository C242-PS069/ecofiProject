package com.dicoding.ecofiproject.ui.recommend

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecofiproject.R

class RecommendActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend) // Pastikan ini dipanggil sebelum findViewById

        // Ambil data dari Intent
        val material = intent.getStringExtra("MATERIAL") ?: "Tidak diketahui"
        val confidence = intent.getStringExtra("CONFIDENCE") ?: "Tidak diketahui"
        val title = intent.getStringExtra("TITLE") ?: "Tidak diketahui"
        val description = intent.getStringExtra("DESCRIPTION") ?: "Tidak tersedia"

        // Tampilkan data ke dalam layout
        findViewById<TextView>(R.id.material_output).text = material
        findViewById<TextView>(R.id.material_accuracy).text = "Akurasi: $confidence"
        findViewById<TextView>(R.id.header_title).text = title
    }
}
