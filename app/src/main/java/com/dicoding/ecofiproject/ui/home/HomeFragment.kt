package com.dicoding.ecofiproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ecofiproject.data.response.ArticlesResponse
import com.dicoding.ecofiproject.databinding.FragmentHomeBinding

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

        // Data artikel
        val articles = listOf(
            ArticlesResponse.Article(
                id = "1",
                title = "Judul Artikel 1",
                description = "Deskripsi Artikel 1",
                image = "https://via.placeholder.com/200"
            ),
            ArticlesResponse.Article(
                id = "2",
                title = "Judul Artikel 2",
                description = "Deskripsi Artikel 2",
                image = "https://via.placeholder.com/200"
            ),
            ArticlesResponse.Article(
                id = "3",
                title = "Judul Artikel 3",
                description = "Deskripsi Artikel 3",
                image = "https://via.placeholder.com/200"
            )
        )

        setupRecyclerView(articles)
    }

    private fun setupRecyclerView(articles: List<ArticlesResponse.Article>) {
        val adapter = ArticleAdapter(articles) { article ->
            navigateToArticleDetail(article.id)
        }
        binding.rvArticles.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvArticles.adapter = adapter
        binding.rvArticles.setHasFixedSize(true)
    }

    private fun navigateToArticleDetail(articleId: String) {
        // Implementasi navigasi ke detail artikel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
