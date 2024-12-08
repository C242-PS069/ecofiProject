package com.dicoding.ecofiproject.ui.recommend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ecofiproject.R
import com.dicoding.ecofiproject.databinding.ActivityRecommendBinding

class RecommendActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Logika untuk kembali
        binding.backIcon.setOnClickListener {
            onBackPressed()  // Kembali ke aktivitas sebelumnya
        }

        // Data untuk rekomendasi
        val recommendationItems = listOf(
            RecommendItem("Tempat Pensil", "Penasaran bagaimana caranya? Ayo sini"),
            RecommendItem("Tempat Botol", "Bahan sederhana dengan hasil maksimal"),
            RecommendItem("Tempat Sampah", "Mudah dibuat dan ramah lingkungan")
        )

        // Setup RecyclerView
        val adapter = RecommendAdapter(recommendationItems)
        binding.recommendationList.layoutManager = LinearLayoutManager(this)
        binding.recommendationList.adapter = adapter
    }
}
