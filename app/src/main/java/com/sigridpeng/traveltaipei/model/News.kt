package com.sigridpeng.traveltaipei.model

import java.io.Serializable

data class News(
    val id: Long,
    val title: String,
    val description: String,
    val url: String
) : Serializable

data class NewsResponse(
    val total: Int,
    val data: List<News>
)