package com.sigridpeng.traveltaipei.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sigridpeng.traveltaipei.R
import com.sigridpeng.traveltaipei.model.News

class NewsAdapter(private var newsList: List<News>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(news: News)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentNews = newsList[position]
        with(holder) {
            tvTitle.text = currentNews.title
            tvContent.text = currentNews.description

            itemView.setOnClickListener {
                listener.onItemClick(currentNews)
            }
        }
    }

    override fun getItemCount() = newsList.size

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_item_news_title)
        val tvContent: TextView = itemView.findViewById(R.id.tv_item_news_content)
    }

}


