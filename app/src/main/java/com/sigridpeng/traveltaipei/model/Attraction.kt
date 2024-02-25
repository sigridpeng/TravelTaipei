package com.sigridpeng.traveltaipei.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Attraction(
    val id: Long,
    val name: String,
    val introduction: String,
    @SerializedName("open_time")
    val openTime: String,
    val address: String,
    val tel: String,
    @SerializedName("official_site")
    val officialSite: String,
    val images: List<AttractionImage>,
    val url: String
) : Serializable

data class AttractionImage(
    val src: String,
    val subject: String,
    val ext: String
) : Serializable

data class AttractionResponse(
    val total: Int,
    val data: List<Attraction>
)