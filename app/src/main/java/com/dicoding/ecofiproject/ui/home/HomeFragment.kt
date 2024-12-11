package com.dicoding.ecofiproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ecofiproject.data.api.ApiConfig
import com.dicoding.ecofiproject.data.pref.UserPreference
import com.dicoding.ecofiproject.data.pref.dataStore
import com.dicoding.ecofiproject.data.response.ArticlesResponse
import com.dicoding.ecofiproject.databinding.FragmentHomeBinding
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
    }

    private fun loadArticles() {
        lifecycleScope.launch {
            try {
                val session = UserPreference.getInstance(requireContext().dataStore).getSession().first()
                if (session.token.isNotEmpty()) {
                    val response = ApiConfig.getApiService(session.token).getAllArticles()
                    if (response.isSuccessful && response.body() != null) {
                        val articles = response.body()!!.data
                        setupRecyclerView(articles)
                    } else {
                        Toast.makeText(requireContext(), "Gagal memuat artikel", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Token tidak ditemukan, silakan login", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setupRecyclerView(articles: List<ArticlesResponse.Article>) {
        val adapter = ArticleAdapter(articles) { article ->
            navigateToArticleDetail(article.id.toString())
        }
        binding.rvArticles.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvArticles.adapter = adapter
        binding.rvArticles.setHasFixedSize(true)
    }


    private fun navigateToArticleDetail(articleId: String) {
        Toast.makeText(requireContext(), "Buka artikel ID: $articleId", Toast.LENGTH_SHORT).show()
        // Implement navigasi ke detail artikel jika diperlukan
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
