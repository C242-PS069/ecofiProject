package com.dicoding.ecofiproject.ui.recommend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ecofiproject.R
import com.squareup.picasso.Picasso

data class Recommendation(
    val title: String,
    val description: String,
    val imageUrl: String
)

class RecommendationAdapter(private val recommendations: List<Recommendation>) :
    RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recommend, parent, false)
        return RecommendationViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val recommendation = recommendations[position]
        holder.titleTextView.text = recommendation.title
        holder.descriptionTextView.text = recommendation.description
        if (recommendation.imageUrl.isNotEmpty()) {
            Picasso.get().load(recommendation.imageUrl).into(holder.imageView)
        }
    }

    override fun getItemCount(): Int = recommendations.size

    class RecommendationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.material_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.material_description)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}