package com.example.dailyfeed.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyfeed.Models.Article
import com.example.dailyfeed.Models.NewsResponse
import com.example.dailyfeed.Repository.NewsRepository
import com.example.dailyfeed.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
):ViewModel() {
    val BreakingNews: MutableLiveData<Resource<NewsResponse>> =MutableLiveData()
    var breakingNewsPage=1
    var breakResponse:NewsResponse?=null
    val searchNews: MutableLiveData<Resource<NewsResponse>> =MutableLiveData()
    var searchNewsPage=1
    var searchResponse:NewsResponse?=null
    init {
        getBreakingNews("in")
    }
    fun getBreakingNews(countryCode:String)=viewModelScope.launch {
        BreakingNews.postValue(Resource.Loading())
        val response=newsRepository.getBreakingNews(countryCode,breakingNewsPage)
        BreakingNews.postValue(handleBreaking(response))
    }
    fun searchNews(searchQuery:String)=viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response=newsRepository.searchNews(searchQuery,searchNewsPage)
        searchNews.postValue(handleSearch(response))
    }
    private fun handleBreaking(response:Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {result->
                breakingNewsPage++
                if(breakResponse==null){
                    breakResponse=result
                }else{
                    val oldArti=breakResponse?.articles
                    val newArti=result.articles
                    oldArti?.addAll(newArti)
                }
                return Resource.Sucess(breakResponse?:result)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleSearch(response:Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {result->
                searchNewsPage++
                if(searchResponse==null){
                    searchResponse=result
                }else{
                    val oldArti=searchResponse?.articles
                    val newArti=result.articles
                    oldArti?.addAll(newArti)
                }
                return Resource.Sucess(searchResponse?:result)
            }
        }
        return Resource.Error(response.message())
    }
    //savednews Fragment:viewmoddel ko repository
    fun addArticle(article: Article)=viewModelScope.launch {
        newsRepository.upsert(article)
    }
    fun deleteArticle(article: Article)=viewModelScope.launch {
        newsRepository.delete(article)
    }
    fun getSavedNews()=newsRepository.getSavedNews()
}