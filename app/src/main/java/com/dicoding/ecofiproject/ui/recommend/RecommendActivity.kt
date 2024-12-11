package com.dicoding.ecofiproject.ui.recommend

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ecofiproject.R
import com.squareup.picasso.Picasso

class RecommendActivity : AppCompatActivity() {

    private lateinit var materialTextView: TextView
    private lateinit var confidenceTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var imageView: ImageView

    private val recommendViewModel: RecommendViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)

        materialTextView = findViewById(R.id.material_output)
        confidenceTextView = findViewById(R.id.material_accuracy)
        titleTextView = findViewById(R.id.material_title)
        descriptionTextView = findViewById(R.id.material_description)
        imageView = findViewById(R.id.imageView)

        val material = intent.getStringExtra("MATERIAL") ?: "No material"
        val confidence = intent.getStringExtra("CONFIDENCE") ?: "No confidence"
        val title = intent.getStringExtra("TITLE") ?: "Unknown Title"
        val description = intent.getStringExtra("DESCRIPTION") ?: "No Description"
        val imageUrl = intent.getStringExtra("IMAGE_URL") ?: ""


        recommendViewModel.setData(material, confidence, title, description, imageUrl)


        materialTextView.text = recommendViewModel.material
        confidenceTextView.text = recommendViewModel.confidence
        titleTextView.text = recommendViewModel.title
        descriptionTextView.text = recommendViewModel.description


        if (recommendViewModel.imageUrl.isNotEmpty()) {
            Picasso.get().load(recommendViewModel.imageUrl).into(imageView)

            // Sample data for recommendations
            val recommendations = listOf(
                Recommendation("$title", "$description", "$imageUrl"),
                Recommendation("$title", "$description", "$imageUrl"),
                Recommendation("$title", "$description", "$imageUrl"),
            )

            val recommendationAdapter = RecommendationAdapter(recommendations)
            val recyclerView: RecyclerView = findViewById(R.id.recommendation_list)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = recommendationAdapter

        }
    }
}
