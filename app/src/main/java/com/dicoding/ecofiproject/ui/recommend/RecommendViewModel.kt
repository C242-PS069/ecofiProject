package com.dicoding.ecofiproject.ui.recommend

import androidx.lifecycle.ViewModel

class RecommendViewModel : ViewModel() {

    var material: String = "No material"
    var confidence: String = "No confidence"
    var title: String = "Unknown Title"
    var description: String = "No Description"
    var imageUrl: String = ""

    fun setData(material: String, confidence: String, title: String, description: String, imageUrl: String) {
        this.material = material
        this.confidence = confidence
        this.title = title
        this.description = description
        this.imageUrl = imageUrl
    }
}
