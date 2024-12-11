package com.dicoding.ecofiproject.data.response

data class ArticlesResponse(
    val status: String,
    val message: String,
    val data: List<Article>
) {
    data class Article(
        val id: String,
        val title: String,
        val description: String,
        val image: String
    )
}
