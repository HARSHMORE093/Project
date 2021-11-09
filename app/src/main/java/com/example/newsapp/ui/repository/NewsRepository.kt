package com.example.newsapp.ui.repository

import com.example.newsapp.ui.db.ArticleDatabase
import com.example.newsapp.ui.retrofit.RetrofitInstance

class NewsRepository(
    val db:ArticleDatabase
) {
    suspend fun getBrakingNews(countryCode:String,pageNumber:Int)=
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery:String,pageNumber:Int)=
        RetrofitInstance.api.searchForNews(searchQuery,pageNumber)

}