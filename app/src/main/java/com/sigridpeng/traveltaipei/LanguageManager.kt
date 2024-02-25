package com.sigridpeng.traveltaipei

import android.content.Context
import android.preference.PreferenceManager
import com.sigridpeng.traveltaipei.model.Language
import java.util.*

object LanguageManager {

    private const val SELECTED_LANGUAGE = "selected_language"

    fun setAppLanguage(context: Context, locale: Locale) {
        updateLocale(context, locale)
        println(locale.country)
        // 保存选择的语言，以便下次启动应用时使用
        val lang = getLanguage(locale)
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(SELECTED_LANGUAGE, lang)
            .apply()
    }

    fun getPreferLanguage(context: Context): String {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getString(SELECTED_LANGUAGE, getLanguage(Locale.getDefault()))
            ?: getLanguage(Locale.getDefault())
    }

    private fun updateLocale(context: Context, locale: Locale) {
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = resources.configuration

        configuration.setLocale(locale)
        context.createConfigurationContext(configuration)
    }

    fun getLanguage(locale: Locale): String {
        val lang = when (locale) {
            Locale.TAIWAN -> Language.TAIWAN.code
            Locale.CHINA -> Language.CHINA.code
            Locale.ENGLISH -> Language.USA.code
            else -> Language.USA.code
        }
        return lang
    }

    fun getLocale(lang: String): Locale {
        val locale = when (lang) {
            Language.TAIWAN.code -> Locale.TAIWAN
            Language.CHINA.code -> Locale.CHINA
            Language.USA.code -> Locale.ENGLISH
            else -> Locale.ENGLISH
        }
        return locale
    }
}
