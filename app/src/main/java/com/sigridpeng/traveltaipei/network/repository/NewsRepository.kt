package com.sigridpeng.traveltaipei.network.repository

import com.sigridpeng.traveltaipei.network.api.ApiService
import com.sigridpeng.traveltaipei.model.News

class NewsRepository(private val apiService: ApiService) {

    suspend fun searchNews(lang: String, page: Int): List<News> {
        val response = apiService.getNews(lang = lang, page = page)
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception()
        }
    }
}
