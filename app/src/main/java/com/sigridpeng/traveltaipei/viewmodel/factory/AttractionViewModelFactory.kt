package com.sigridpeng.traveltaipei.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sigridpeng.traveltaipei.network.repository.AttractionRepository
import com.sigridpeng.traveltaipei.viewmodel.AttractionViewModel

class AttractionViewModelFactory constructor(
    private val context: Context,
    private val repository: AttractionRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(AttractionViewModel::class.java) -> AttractionViewModel(
                    context,
                    repository
                )

                else ->
                    throw IllegalArgumentException(
                        "Unknown ViewModel class: ${modelClass.name}"
                    )
            }
        } as T
}