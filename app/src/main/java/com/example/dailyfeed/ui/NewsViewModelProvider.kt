package com.example.dailyfeed.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dailyfeed.Repository.NewsRepository

@Suppress("UNCHECKED_CAST")
class NewsViewModelProvider(val newsRepository: NewsRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository)as T
    }
}