package com.dicoding.ecofiproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.ecofiproject.data.api.ApiConfig
import com.dicoding.ecofiproject.data.response.ArticlesResponse
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _articles = MutableLiveData<List<ArticlesResponse.Article>>()
    val articles: LiveData<List<ArticlesResponse.Article>> = _articles

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchArticles() {
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getAllArticles()
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        _articles.value = it
                    }
                } else {
                    _errorMessage.value = "Failed to fetch articles: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred: ${e.message}"
            }
        }
    }
}
