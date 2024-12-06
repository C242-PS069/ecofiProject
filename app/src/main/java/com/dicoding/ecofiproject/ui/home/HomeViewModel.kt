package com.dicoding.ecofiproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.ecofiproject.data.api.ApiService
import com.dicoding.ecofiproject.data.response.ArticlesResponse
import com.dicoding.ecofiproject.data.response.BannersResponse
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://ecofy-373030918770.asia-southeast2.run.app") // Ganti dengan base URL API Anda
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    private val _articles = MutableLiveData<List<ArticlesResponse.Article>>()
    val articles: LiveData<List<ArticlesResponse.Article>> get() = _articles

    private val _banners = MutableLiveData<List<BannersResponse.Banner>>()
    val banners: LiveData<List<BannersResponse.Banner>> get() = _banners

    fun fetchArticles() {
        viewModelScope.launch {
            val response = apiService.getAllArticles()
            if (response.isSuccessful) {
                _articles.postValue(response.body()?.data)
            }
        }
    }

    fun fetchBanners() {
        viewModelScope.launch {
            val response = apiService.getAllBanners()
            if (response.isSuccessful) {
                _banners.postValue(response.body()?.data)
            }
        }
    }
}
