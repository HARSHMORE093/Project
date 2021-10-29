package com.example.newsapp.ui.ui

import androidx.lifecycle.ViewModel
import com.example.newsapp.ui.repository.NewsRepository

class NewsViewModel(
    val newsRepository: NewsRepository
):ViewModel() {

}