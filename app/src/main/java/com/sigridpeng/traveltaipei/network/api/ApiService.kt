package com.sigridpeng.traveltaipei.network.api

import com.sigridpeng.traveltaipei.model.AttractionResponse
import com.sigridpeng.traveltaipei.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("{lang}/Events/News")
    suspend fun getNews(
        @Header("accept") acceptHeaderValue: String = "application/json",
        @Path("lang") lang: String,
        @Query("page") page: Int = 1,
    ): Response<NewsResponse>

    @GET("{lang}/Attractions/All")
    suspend fun getAttraction(
        @Header("accept") acceptHeaderValue: String = "application/json",
        @Path("lang") lang: String,
        @Query("page") page: Int = 1,
    ): Response<AttractionResponse>
}