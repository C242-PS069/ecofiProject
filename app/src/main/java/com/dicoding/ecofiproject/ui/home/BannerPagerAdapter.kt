package com.dicoding.ecofiproject.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.dicoding.ecofiproject.R
import com.dicoding.ecofiproject.data.response.BannersResponse

class BannerPagerAdapter(private val banners: List<BannersResponse.Banner>) : PagerAdapter() {

    override fun getCount(): Int {
        return banners.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.item_banner, container, false)

        val imageView: ImageView = view.findViewById(R.id.bannerImage)
        Glide.with(container.context)
            .load(banners[position].image)
            .into(imageView)

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
