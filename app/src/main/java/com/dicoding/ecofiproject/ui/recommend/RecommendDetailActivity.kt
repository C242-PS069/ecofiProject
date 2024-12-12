package com.dicoding.ecofiproject.ui.recommend

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.ecofiproject.data.api.ApiConfig
import com.dicoding.ecofiproject.data.pref.UserPreference
import com.dicoding.ecofiproject.data.pref.dataStore
import com.dicoding.ecofiproject.data.response.RecycleData
import com.dicoding.ecofiproject.databinding.ActivityRecommendationDetailBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RecommendDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("ID", -1) // Ambil ID dari Intent
        if (id != -1) {
            loadRecommendationDetails(id)
        } else {
            Toast.makeText(this, "ID tidak valid", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadRecommendationDetails(id: Int) {
        lifecycleScope.launch {
            try {
                val context = this@RecommendDetailActivity
                val session = UserPreference.getInstance(context.dataStore).getSession().first()

                if (session.token.isNotEmpty()) {
                    val response = ApiConfig.getApiService(session.token).getRecycleById(id)
                    if (response.isSuccessful && response.body() != null) {
                        val recycleDetails = response.body()!!.data
                        displayDetails(recycleDetails)
                    } else {
                        Toast.makeText(context, "Gagal memuat detail rekomendasi", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Token tidak ditemukan, silakan login", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@RecommendDetailActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayDetails(recycleData: RecycleData) {
        // Set judul dan deskripsi
        binding.recommendationTitle.text = recycleData.title
        binding.recommendationDescription.text = recycleData.description

        // Menampilkan bahan dan alat
        val tools = recycleData.tools
        val materialsToolsText = tools.materialsTools.joinToString(separator = "\n") { "- $it" }
        binding.toolsTitle.text = tools.title
        binding.toolsList.text = materialsToolsText

        // Menampilkan langkah pembuatan
        val makes = recycleData.makes
        val stepsText = makes.step.joinToString(separator = "\n") { "- $it" }
        binding.makesTitle.text = makes.title
        binding.makesSteps.text = stepsText

        // Memastikan video ditampilkan dengan benar
        val youTubePlayerView = binding.recommendationVideo
        lifecycle.addObserver(youTubePlayerView)

        if (recycleData.video.isNullOrEmpty()) {
            Toast.makeText(this, "Video tidak tersedia", Toast.LENGTH_SHORT).show()
        } else {
            val videoId = extractVideoId(recycleData.video)
            if (videoId.isNullOrEmpty()) {
                Toast.makeText(this, "Video tidak valid", Toast.LENGTH_SHORT).show()
            } else {
                youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(videoId, 0f)
                    }
                })
            }
        }
    }

    private fun extractVideoId(videoUrl: String): String? {
        val regex = "(?<=v=|youtu\\.be/|embed/|watch\\?v=)([^&?\\n]+)".toRegex()
        return regex.find(videoUrl)?.value
    }
}
