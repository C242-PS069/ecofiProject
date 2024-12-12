package com.dicoding.ecofiproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.dicoding.ecofiproject.data.api.ApiConfig
import com.dicoding.ecofiproject.data.pref.UserPreference
import com.dicoding.ecofiproject.data.pref.dataStore
import com.dicoding.ecofiproject.data.response.ArticlesResponse
import com.dicoding.ecofiproject.data.response.BannersResponse
import com.dicoding.ecofiproject.databinding.FragmentHomeBinding
import com.dicoding.ecofiproject.ui.home.DetailArticleActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadArticles()
        loadBanners()
    }

    private fun loadArticles() {
        lifecycleScope.launch {
            try {
                val context = context ?: return@launch

                // Ambil token dari UserPreference
                val session = UserPreference.getInstance(context.dataStore).getSession().first()
                if (session.token.isNotEmpty()) {
                    val response = ApiConfig.getApiService(session.token).getAllArticles()
                    if (response.isSuccessful && response.body() != null) {
                        val articles = response.body()!!.data
                        setupRecyclerView(articles)
                    } else {
                        Toast.makeText(context, "Gagal memuat artikel", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Token tidak ditemukan, silakan login", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                val context = context ?: return@launch
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadBanners() {
        lifecycleScope.launch {
            try {
                val context = context ?: return@launch

                // Ambil token dari UserPreference
                val session = UserPreference.getInstance(context.dataStore).getSession().first()
                if (session.token.isNotEmpty()) {
                    val response = ApiConfig.getApiService(session.token).getAllBanners()
                    if (response.isSuccessful && response.body() != null) {
                        val banners = response.body()!!.data
                        setupBannerSlider(banners)
                    } else {
                        Toast.makeText(context, "Gagal memuat banner", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Token tidak ditemukan, silakan login", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                val context = context ?: return@launch
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView(articles: List<ArticlesResponse.Article>) {
        val adapter = ArticleAdapter(articles) { article ->
            navigateToArticleDetail(article.id)
        }

        binding.rvArticles.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvArticles.adapter = adapter
        binding.rvArticles.setHasFixedSize(true)
    }

    private fun setupBannerSlider(banners: List<BannersResponse.Banner>) {
        val imageList = banners.map { banner ->
            SlideModel(banner.image)
        }

        binding.bannerImage.setImageList(imageList)
    }

    private fun navigateToArticleDetail(articleId: Int) {
        val intent = Intent(requireContext(), DetailArticleActivity::class.java)
        intent.putExtra(DetailArticleActivity.EXTRA_ARTICLE_ID, articleId)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
