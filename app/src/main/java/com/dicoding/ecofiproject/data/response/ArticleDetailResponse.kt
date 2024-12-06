package com.dicoding.ecofiproject.data.response

data class ArticleDetailResponse(
    val status: String,
    val message: String,
    val data: ArticleDetail
) {
    data class ArticleDetail(
        val id: Int,
        val title: String,
        val content: List<ContentSection>,
        val video: String?
    ) {
        data class ContentSection(
            val section: String,
            val text: String
        )
    }
}
