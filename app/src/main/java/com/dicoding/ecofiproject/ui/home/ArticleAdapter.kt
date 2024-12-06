package com.dicoding.ecofiproject.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.ecofiproject.R
import com.dicoding.ecofiproject.data.response.ArticlesResponse
import com.dicoding.ecofiproject.databinding.ItemArticleBinding

class ArticleAdapter(
    private val articles: List<ArticlesResponse.Article>,
    private val onReadMoreClick: (ArticlesResponse.Article) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticlesResponse.Article) {
            binding.articleTitle.text = article.title
            binding.articleDescription.text = article.description
            Glide.with(binding.cover.context)
                .load(article.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.cover)

            binding.readMore.setOnClickListener {
                onReadMoreClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size
}
