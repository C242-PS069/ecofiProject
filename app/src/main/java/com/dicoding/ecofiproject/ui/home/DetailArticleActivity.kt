package com.dicoding.ecofiproject.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.ecofiproject.R
import com.dicoding.ecofiproject.data.api.ApiConfig
import com.dicoding.ecofiproject.data.pref.UserPreference
import com.dicoding.ecofiproject.data.pref.dataStore
import com.dicoding.ecofiproject.data.response.ArticleDetailResponse
import com.dicoding.ecofiproject.databinding.ActivityDetailArticleBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArticleBinding
    private var articleId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        articleId = intent.getIntExtra(EXTRA_ARTICLE_ID, -1)
        if (articleId == -1) {
            Toast.makeText(this, "Artikel tidak valid", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        loadArticleDetail()
    }

    private fun loadArticleDetail() {
        lifecycleScope.launch {
            try {
                val session = UserPreference.getInstance(dataStore).getSession().first()
                if (session.token.isNotEmpty()) {
                    val response = ApiConfig.getApiService(session.token).getArticleById(articleId)
                    if (response.isSuccessful && response.body() != null) {
                        val articleDetail = response.body()!!.data
                        showArticleDetail(articleDetail)
                    } else {
                        Toast.makeText(
                            this@DetailArticleActivity,
                            "Gagal memuat detail artikel",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@DetailArticleActivity,
                        "Token tidak ditemukan, silakan login",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@DetailArticleActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showArticleDetail(articleDetail: ArticleDetailResponse.ArticleDetail) {
        binding.articleTitle.text = articleDetail.title
        binding.articleDescription.text = articleDetail.content.joinToString("\n") { it.text }

        val youTubePlayerView = findViewById<YouTubePlayerView>(R.id.article_vidio)
        lifecycle.addObserver(youTubePlayerView)

        val videoUrl = articleDetail.video

        if (videoUrl.isNullOrBlank()) {
            Toast.makeText(this, "URL video tidak tersedia", Toast.LENGTH_SHORT).show()
        } else {
            val videoId = extractVideoId(videoUrl)
            if (videoId.isNullOrBlank()) {
                Toast.makeText(this, "ID video tidak valid", Toast.LENGTH_SHORT).show()
            } else {
                youTubePlayerView.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
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

    companion object {
        const val EXTRA_ARTICLE_ID = "extra_article_id"
    }
}
