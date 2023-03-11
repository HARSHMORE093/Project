package com.example.dailyfeed.Repository

import com.example.dailyfeed.Models.Article
import com.example.dailyfeed.api.RetrofitInstance
import com.example.dailyfeed.db.ArticleDatabase

class NewsRepository(val db:ArticleDatabase) {
    suspend fun getBreakingNews(countryCode:String,pageNumber:Int)=
        RetrofitInstance.api.getBreakinNews(countryCode,pageNumber)

    suspend fun searchNews(searchQuery:String,pageNumber:Int)=
        RetrofitInstance.api.searchForNews(searchQuery,pageNumber)
    suspend fun upsert(article:Article)=db.getArticleDao().upsert(article)
    fun getSavedNews()=db.getArticleDao().getAllArticles()
    suspend fun delete(article: Article)=db.getArticleDao().deleteArticle(article)
}