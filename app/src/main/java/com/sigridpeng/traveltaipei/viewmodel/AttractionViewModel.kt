package com.sigridpeng.traveltaipei.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigridpeng.traveltaipei.LanguageManager
import com.sigridpeng.traveltaipei.model.Attraction
import com.sigridpeng.traveltaipei.network.repository.AttractionRepository
import kotlinx.coroutines.launch
import java.util.Locale

class AttractionViewModel(context: Context, private val repository: AttractionRepository) :
    ViewModel() {

    private val _attractionList = MutableLiveData<List<Attraction>>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _isError = MutableLiveData(false)
    private val _locale = MutableLiveData(Locale.getDefault())
    val attractionList: LiveData<List<Attraction>> = _attractionList
    val isLoading: LiveData<Boolean> = _isLoading
    val isError: LiveData<Boolean> = _isError
    private val defaultPage: Int = 1
    private val defaultItemNum = 2

    init {
        getInitData(LanguageManager.getPreferLanguage(context))
    }

    private fun searchAttraction(lang: String, page: Int, isShort: Boolean) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val attractionList = repository.searchAttraction(lang, page)
                if (isShort) {
                    val shortList: ArrayList<Attraction> = ArrayList()
                    for (i in 0..defaultItemNum) {
                        shortList.add(attractionList[i])
                    }
                    _attractionList.value = shortList
                } else {
                    _attractionList.value = attractionList
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getInitData(lang: String) {
        searchAttractionShortList(lang, defaultPage)

    }

    private fun searchAttractionShortList(lang: String, page: Int) {
        searchAttraction(lang, page, true)
    }

    fun changeLocale(locale: Locale) {
        _locale.value = locale
        getInitData(LanguageManager.getLanguage(locale))
    }

    fun refreshData(context: Context){
        getInitData(LanguageManager.getPreferLanguage(context))
    }
}

