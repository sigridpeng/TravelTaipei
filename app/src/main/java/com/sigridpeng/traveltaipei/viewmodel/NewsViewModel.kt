package com.sigridpeng.traveltaipei.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigridpeng.traveltaipei.LanguageManager
import com.sigridpeng.traveltaipei.model.News
import com.sigridpeng.traveltaipei.network.repository.NewsRepository
import kotlinx.coroutines.launch
import java.util.Locale

class NewsViewModel(context: Context, private val repository: NewsRepository) : ViewModel() {

    private val _newsList = MutableLiveData<List<News>>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _isError = MutableLiveData(false)
    private val _locale = MutableLiveData(Locale.getDefault())
    val newsList: LiveData<List<News>> = _newsList
    val isLoading: LiveData<Boolean> = _isLoading
    val isError: LiveData<Boolean> = _isError
    private val defaultPage: Int = 1
    private val defaultItemNum = 2

    init {
        getInitData(LanguageManager.getPreferLanguage(context))
    }

    private fun searchNews(lang: String, page: Int, isShort: Boolean) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val newsList = repository.searchNews(lang, page)
                if (isShort) {
                    val shortList: ArrayList<News> = ArrayList()
                    for (i in 0..defaultItemNum) {
                        shortList.add(newsList[i])
                    }
                    _newsList.value = shortList
                } else {
                    _newsList.value = newsList
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
        searchNewsShortList(lang, defaultPage)

    }

    private fun searchNewsShortList(lang: String, page: Int) {
        searchNews(lang, page, true)
    }

    fun changeLocale(locale: Locale) {
        _locale.value = locale
        getInitData(LanguageManager.getLanguage(locale))
    }

    fun refreshData(context: Context){
        getInitData(LanguageManager.getPreferLanguage(context))
    }
}

