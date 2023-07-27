package com.taomish.app.android.farmsanta.farmer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.interfaces.OnRecyclerItemClickListener
import com.taomish.app.android.farmsanta.farmer.models.api.home.News

class NewsAllRecyclerAdapter(private val dataSet: ArrayList<News>, requireContext: Context) :
    RecyclerView.Adapter<NewsAllRecyclerAdapter.NewsViewHolder>() {

    val context                                 = requireContext

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsImageView: ImageView            = itemView.findViewById(R.id.news_image_img)
        var contentTextView: TextView           = itemView.findViewById(R.id.news_text_content)
        var titleTextView: TextView             = itemView.findViewById(R.id.news_text_title)
    }

    private var itemClickListener: OnRecyclerItemClickListener? = null

    fun setItemClickListener(itemClickListener: OnRecyclerItemClickListener) {
        this.itemClickListener                  = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view                                = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, listPosition: Int) {
        val news                                = dataSet[listPosition]
        holder.contentTextView.text             = news.content
        holder.titleTextView.text               = news.title

        Glide.with(context)
            .load(news.image)
            .into(holder.newsImageView)

        holder.itemView.setOnClickListener { v: View ->
            itemClickListener?.onItemClick(v, "news", listPosition)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}