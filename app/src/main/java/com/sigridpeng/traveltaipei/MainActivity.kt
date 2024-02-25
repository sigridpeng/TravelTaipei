package com.sigridpeng.traveltaipei

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sigridpeng.traveltaipei.ui.HomeFragment
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, homeFragment)
            .commit()
    }

    override fun attachBaseContext(newBase: Context) {
        val language = LanguageManager.getLocale(LanguageManager.getPreferLanguage(newBase))
        val context = ContextUtils.updateLocale(newBase, language)
        super.attachBaseContext(context)
    }

    object ContextUtils {
        fun updateLocale(context: Context, locale: Locale): Context {
            val configuration = Configuration(context.resources.configuration)
            configuration.setLocale(locale)
            return context.createConfigurationContext(configuration)
        }
    }
}