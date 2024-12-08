package com.dicoding.ecofiproject.ui.recommend

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ecofiproject.R
import com.dicoding.ecofiproject.databinding.ItemRecommendBinding

class RecommendAdapter(private val items: List<RecommendItem>) : RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder>() {

    // Membuat ViewHolder untuk item
    inner class RecommendViewHolder(val binding: ItemRecommendBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                // Dapatkan posisi item yang diklik
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Ambil item yang dipilih
                    val selectedItem = items[position]
                    // Intent untuk menavigasi ke DetailActivity
                    val intent = Intent(it.context, RecommendDetailActivity::class.java)
                    intent.putExtra("ITEM_TITLE", selectedItem.title)
                    intent.putExtra("ITEM_DESCRIPTION", selectedItem.description)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    // Mengikat data ke item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val binding = ItemRecommendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendViewHolder(binding)
    }

    // Bind data ke item
    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        val item = items[position]
        holder.binding.itemTitle.text = item.title
        holder.binding.itemDescription.text = item.description
    }

    override fun getItemCount() = items.size
}
