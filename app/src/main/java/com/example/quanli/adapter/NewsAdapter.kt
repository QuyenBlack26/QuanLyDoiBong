package com.example.quanli.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quanli.R
import com.example.quanli.model.News

class NewsAdapter(
    private var newsList: List<News>,
    private val onItemClick: (News) -> Unit,
    private val onItemLongClick: (News) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newsImage: ImageView = view.findViewById(R.id.newsImage)
        val newsTitle: TextView = view.findViewById(R.id.newsTitle)
        val newsDate: TextView = view.findViewById(R.id.newsDate)
        val newsSummary: TextView = view.findViewById(R.id.newsSummary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.newsTitle.text = news.title
        holder.newsDate.text = news.date
        holder.newsSummary.text = news.content

        Glide.with(holder.itemView.context)
            .load(news.imageUrl)
            .placeholder(android.R.color.darker_gray)
            .into(holder.newsImage)

        holder.itemView.setOnClickListener { onItemClick(news) }
        holder.itemView.setOnLongClickListener {
            onItemLongClick(news)
            true
        }
    }

    override fun getItemCount(): Int = newsList.size

    fun updateData(newNewsList: List<News>) {
        newsList = newNewsList
        notifyDataSetChanged()
    }
}
