package com.example.newsapp.ui.db

import android.content.Context
import androidx.room.*
import com.example.newsapp.ui.modles.Article

@Database(
    entities = [Article::class],//1 entities link kar de
    version = 1 ,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun getArticleDao():ArticleDao//2 Dao bhi link kar diya

    //3 Singleton class:->
    companion object{
        //Volatile:--> instance mai jase hi value assign hoti vase hi sare theards ko pata pada jata hai aur value update ho gati hai.
        @Volatile
        private var instance:ArticleDatabase? = null
        private val LOCK=Any()

        operator fun invoke(context: Context)= instance?: synchronized(LOCK){//synchorniced lock used for 2,3 theards ek sathh kam nahi kare
            instance?:createDatabase(context).also { instance=it}
        }
        private fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }

}