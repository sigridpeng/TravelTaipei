package com.sigridpeng.traveltaipei.network.repository

import com.sigridpeng.traveltaipei.model.Attraction
import com.sigridpeng.traveltaipei.network.api.ApiService

class AttractionRepository(private val apiService: ApiService) {

    suspend fun searchAttraction(lang: String, page: Int): List<Attraction> {
        val response = apiService.getAttraction(lang = lang, page = page)
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception()
        }
    }
}
