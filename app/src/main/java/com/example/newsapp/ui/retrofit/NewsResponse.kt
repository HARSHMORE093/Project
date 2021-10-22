package com.example.newsapp.ui.retrofit

import com.example.newsapp.ui.retrofit.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)