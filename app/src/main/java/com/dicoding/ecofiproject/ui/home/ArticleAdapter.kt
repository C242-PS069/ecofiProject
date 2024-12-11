package com.dicoding.ecofiproject.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.ecofiproject.R
import com.dicoding.ecofiproject.data.response.ArticlesResponse
import com.dicoding.ecofiproject.databinding.ItemArticleBinding

class ArticleAdapter(
    private val articles: List<ArticlesResponse.Article>,
    private val onItemClick: (ArticlesResponse.Article) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article, onItemClick)
    }

    override fun getItemCount(): Int = articles.size

    class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticlesResponse.Article, onItemClick: (ArticlesResponse.Article) -> Unit) {
            binding.articleTitle.text = article.title
            binding.articleDescription.text = article.description
            Glide.with(itemView.context)
                .load(article.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.cover)

            itemView.setOnClickListener { onItemClick(article) }
        }
    }
}
