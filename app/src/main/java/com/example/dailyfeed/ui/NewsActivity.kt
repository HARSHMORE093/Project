package com.example.dailyfeed.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.dailyfeed.R
import com.example.dailyfeed.Repository.NewsRepository
import com.example.dailyfeed.databinding.ActivityMainBinding
import com.example.dailyfeed.db.ArticleDatabase

class NewsActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        val navHostFragment= supportFragmentManager.findFragmentById(R.id.newsNavHostFrag) as NavHostFragment
        val navController= navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        val newsRepository=NewsRepository(ArticleDatabase(this))
        val viewModelProvider=NewsViewModelProvider(newsRepository)
        viewModel=ViewModelProvider(this,viewModelProvider).get(NewsViewModel::class.java)
    }
}