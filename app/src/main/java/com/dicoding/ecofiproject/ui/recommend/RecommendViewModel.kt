package com.dicoding.ecofiproject.ui.recommend

import androidx.lifecycle.ViewModel
import com.dicoding.ecofiproject.data.response.DataItem
import java.util.ArrayList

class RecommendViewModel : ViewModel() {

    var material: String = "No material"
    var confidence: String = "No confidence"
    var itemLists: ArrayList<DataItem> = arrayListOf()


    // Metode untuk mengatur data yang diterima dari Intent
    fun setData(material: String, confidence: String, itemLists: ArrayList<DataItem>) {
        this.material = material
        this.confidence = confidence
        this.itemLists = itemLists


    }
}