package com.dicoding.ecofiproject.ui.recommend

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ecofiproject.R

class RecommendActivity : AppCompatActivity() {

    private lateinit var materialTextView: TextView
    private lateinit var confidenceTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var imageView: ImageView

    private val recommendViewModel: RecommendViewModel by viewModels()  // ViewModel instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)

        // Initialize views
        materialTextView = findViewById(R.id.material_output)
        confidenceTextView = findViewById(R.id.material_accuracy)
        titleTextView = findViewById(R.id.material_title)
        descriptionTextView = findViewById(R.id.material_description)
        imageView = findViewById(R.id.imageView)

        // Get data from intent and pass to ViewModel
        val material = intent.getStringExtra("MATERIAL") ?: "No material"
        val confidence = intent.getStringExtra("CONFIDENCE") ?: "No confidence"
        val itemLists =
            intent.getParcelableArrayListExtra<com.dicoding.ecofiproject.data.response.DataItem>("ITEMLIST")

        // Set data in ViewModel
        recommendViewModel.setData(material, confidence, itemLists ?: arrayListOf())

        // Set data to the TextViews using ViewModel
        materialTextView.text = recommendViewModel.material
        confidenceTextView.text = recommendViewModel.confidence

        // Load image using Picasso if URL is not empty

        // Sample data for recommendations


        val recommendationAdapter = RecommendationAdapter(itemLists)
        val recyclerView: RecyclerView = findViewById(R.id.recommendation_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recommendationAdapter


    }
}