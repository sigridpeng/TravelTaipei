package com.sigridpeng.traveltaipei.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sigridpeng.traveltaipei.R
import com.sigridpeng.traveltaipei.model.Attraction

class AttractionAdapter(
    private var attractionList: List<Attraction>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<AttractionAdapter.AttractionViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(attraction: Attraction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_attraction, parent, false)
        return AttractionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AttractionViewHolder, position: Int) {
        val currentAttraction = attractionList[position]
        with(holder) {
            tvTitle.text = currentAttraction.name
            tvContent.text = currentAttraction.introduction

            if (currentAttraction.images.isEmpty()) {
                ivPhoto.setImageResource(R.drawable.ic_photo)
            } else {
                val attractionImage = currentAttraction.images[0]
                Glide.with(ivPhoto)
                    .load(attractionImage.src)
                    .placeholder(R.drawable.ic_downloading)
                    .error(R.drawable.ic_warning)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(ivPhoto)
            }

            itemView.setOnClickListener {
                listener.onItemClick(currentAttraction)
            }
        }
    }

    override fun getItemCount() = attractionList.size

    inner class AttractionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_item_attraction_title)
        val tvContent: TextView = itemView.findViewById(R.id.tv_item_attraction_content)
        val ivPhoto: ImageView = itemView.findViewById(R.id.iv_item_attraction)
    }
}


