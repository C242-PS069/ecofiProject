package com.dicoding.ecofiproject.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.ecofiproject.data.response.BannersResponse
import com.dicoding.ecofiproject.databinding.ItemBannerBinding

class BannerAdapter(
    private val banners: List<BannersResponse.Banner>,
    private val onClick: (BannersResponse.Banner) -> Unit
) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(private val binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(banner: BannersResponse.Banner) {
            Glide.with(binding.root.context)
                .load(banner.image)
                .into(binding.bannerImage)
            binding.root.setOnClickListener { onClick(banner) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(banners[position])
    }

    override fun getItemCount(): Int = banners.size
}
