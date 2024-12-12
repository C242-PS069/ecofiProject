package com.dicoding.ecofiproject.ui.recommend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ecofiproject.R
import com.dicoding.ecofiproject.data.response.DataItem
import com.squareup.picasso.Picasso
import java.util.ArrayList

class RecommendationAdapter(
    private val recommendations: ArrayList<DataItem>?,
    private val onItemClick: (DataItem) -> Unit
) : RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recommend, parent, false)
        return RecommendationViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val recommendation = recommendations?.get(position) ?: DataItem(0, "", "", emptyList(), "", "")
        holder.titleTextView.text = recommendation.title
        holder.descriptionTextView.text = recommendation.description
        if (recommendation.image.isNotEmpty()) {
            Picasso.get().load(recommendation.image).into(holder.imageView)
        }

        holder.itemView.setOnClickListener {
            onItemClick(recommendation)
        }
    }

    override fun getItemCount(): Int = recommendations?.size ?: 0

    class RecommendationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.material_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.material_description)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
