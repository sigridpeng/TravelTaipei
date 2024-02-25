package com.sigridpeng.traveltaipei.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sigridpeng.traveltaipei.network.repository.NewsRepository
import com.sigridpeng.traveltaipei.viewmodel.NewsViewModel

class NewsViewModelFactory constructor(
    private val context: Context,
    private val repository: NewsRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(NewsViewModel::class.java) -> NewsViewModel(context, repository)
                else ->
                    throw IllegalArgumentException(
                        "Unknown ViewModel class: ${modelClass.name}"
                    )
            }
        } as T
}