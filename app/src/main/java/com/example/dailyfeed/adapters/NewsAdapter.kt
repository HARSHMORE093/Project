package com.example.dailyfeed.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dailyfeed.Models.Article
import com.example.dailyfeed.R

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.ivArticleImage)
        val title = itemView.findViewById<TextView>(R.id.tvTitle)
        val description = itemView.findViewById<TextView>(R.id.tvDescription)
        val published = itemView.findViewById<TextView>(R.id.tvPublishedAt)
        val source = itemView.findViewById<TextView>(R.id.tvSource)
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_article_preview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(holder.image)
            holder.source.text = article.source?.name
            holder.title.text = article.title
            holder.description.text = article.description
            holder.published.text = article.publishedAt
            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    private var onItemClickListener:((Article)->Unit)?=null
    fun setOnItemClickListener(listener: (Article)->Unit){
        onItemClickListener=listener
    }
}


