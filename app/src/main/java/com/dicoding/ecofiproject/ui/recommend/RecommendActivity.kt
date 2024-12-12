package com.dicoding.ecofiproject.ui.recommend

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ecofiproject.R
import com.dicoding.ecofiproject.data.response.DataItem

class RecommendActivity : AppCompatActivity() {

    private lateinit var materialTextView: TextView
    private lateinit var confidenceTextView: TextView

    private val recommendViewModel: RecommendViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)

        materialTextView = findViewById(R.id.material_output)
        confidenceTextView = findViewById(R.id.material_accuracy)

        val material = intent.getStringExtra("MATERIAL") ?: "No material"
        val confidence = (intent.getStringExtra("CONFIDENCE") + "% accuracy") ?: "No confidence"
        val itemLists =
            intent.getParcelableArrayListExtra<DataItem>("ITEMLIST") ?: arrayListOf()

        recommendViewModel.setData(material, confidence, itemLists)

        materialTextView.text = recommendViewModel.material
        confidenceTextView.text = recommendViewModel.confidence

        val recommendationAdapter = RecommendationAdapter(itemLists) { recommendation ->
            val intent = Intent(this, RecommendDetailActivity::class.java).apply {
                putExtra("ID", recommendation.id) // Kirim ID item ke RecommendDetailActivity
            }
            startActivity(intent)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recommendation_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recommendationAdapter
    }
}
