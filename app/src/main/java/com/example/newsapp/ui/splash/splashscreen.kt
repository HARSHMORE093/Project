package com.example.newsapp.ui.splash


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.ui.ui.NewsActivity

class splashscreen :AppCompatActivity(){

    private val TimeOut:Long=4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)
        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, NewsActivity::class.java))
            finish()
        },TimeOut)
    }

}