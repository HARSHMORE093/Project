package com.example.newsapp.ui.modles

import com.example.newsapp.ui.modles.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)