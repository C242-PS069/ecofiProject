package com.dicoding.ecofiproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ecofiproject.data.api.ApiService
import com.dicoding.ecofiproject.data.response.ArticlesResponse
import com.dicoding.ecofiproject.data.response.BannersResponse
import com.dicoding.ecofiproject.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ecofy-373030918770.asia-southeast2.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        // Load banners and articles from API
        lifecycleScope.launch {
            val bannersResponse = apiService.getAllBanners()
            if (bannersResponse.isSuccessful && bannersResponse.body() != null) {
                setupBanner(bannersResponse.body()!!.data)
            }

            val articlesResponse = apiService.getAllArticles()
            if (articlesResponse.isSuccessful && articlesResponse.body() != null) {
                setupArticles(articlesResponse.body()!!.data)
            } else {
                Toast.makeText(requireContext(), "Failed to load articles", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupBanner(banners: List<BannersResponse.Banner>) {
        val adapter = BannerPagerAdapter(banners)
        binding.viewPageBanner.adapter = adapter
        binding.circleIndicator.setViewPager(binding.viewPageBanner)
    }

    private fun setupArticles(articles: List<ArticlesResponse.Article>) {
        val adapter = ArticleAdapter(articles) { article ->
            navigateToArticleDetail(article.id)
        }
        binding.rvArticles.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvArticles.adapter = adapter
        binding.rvArticles.setHasFixedSize(true)
    }

    private fun navigateToArticleDetail(articleId: Int) {
        // Navigasi ke halaman detail artikel
        Toast.makeText(requireContext(), "Clicked article ID: $articleId", Toast.LENGTH_SHORT).show()
        // Bisa ditambahkan Navigation Component atau Intent
    }

}
