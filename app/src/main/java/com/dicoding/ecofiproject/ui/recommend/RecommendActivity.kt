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

    private val recommendViewModel: RecommendViewModel by viewModels()  // ViewModel instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)

        materialTextView = findViewById(R.id.material_output)
        confidenceTextView = findViewById(R.id.material_accuracy)

        val material = intent.getStringExtra("MATERIAL") ?: "No material"
        val confidence = intent.getStringExtra("CONFIDENCE") ?: "No confidence"
        val itemLists =
            intent.getParcelableArrayListExtra<com.dicoding.ecofiproject.data.response.DataItem>("ITEMLIST")

        recommendViewModel.setData(material, confidence, itemLists ?: arrayListOf())

        materialTextView.text = recommendViewModel.material
        confidenceTextView.text = recommendViewModel.confidence

        val recommendationAdapter = RecommendationAdapter(itemLists)
        val recyclerView: RecyclerView = findViewById(R.id.recommendation_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recommendationAdapter


    }
}