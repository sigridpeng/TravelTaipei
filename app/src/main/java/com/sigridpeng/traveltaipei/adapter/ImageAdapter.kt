package com.sigridpeng.traveltaipei.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sigridpeng.traveltaipei.R
import com.youth.banner.adapter.BannerAdapter

class ImageAdapter(private var dataList: List<String>) :
    BannerAdapter<String, ImageAdapter.ImageViewHolder>(dataList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun getItemCount() = dataList.size
    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageViewHolder {
        val itemView =
            LayoutInflater.from(parent!!.context).inflate(R.layout.item_attraction, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun onBindView(
        holder: ImageViewHolder?,
        data: String?,
        position: Int,
        size: Int
    ) {
        if (data != null && holder != null) {
            with(holder) {
                if (data.isEmpty()) {
                    ivPhoto.setImageResource(R.drawable.ic_photo)
                } else {
                    Glide.with(ivPhoto)
                        .load(data)
                        .placeholder(R.drawable.ic_downloading)
                        .error(R.drawable.ic_warning)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(ivPhoto)
                }
            }
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPhoto: ImageView = itemView.findViewById(R.id.iv_item_image)
    }

}


