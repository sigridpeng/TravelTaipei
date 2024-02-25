package com.sigridpeng.traveltaipei.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.sigridpeng.traveltaipei.LanguageManager
import com.sigridpeng.traveltaipei.R
import com.sigridpeng.traveltaipei.model.Language

class LanguageDialog(context: Context, private val listener: OnSelectListener) : Dialog(context) {

    interface OnSelectListener {
        fun onSelect(language: Language)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_language)
        val tvTaiwan = findViewById<TextView>(R.id.tv_item_language_zh_tw)
        val tvChina = findViewById<TextView>(R.id.tv_item_language_zh_cn)
        val tvUSA = findViewById<TextView>(R.id.tv_item_language_en)

        setLanguageClick(tvTaiwan, Language.TAIWAN)
        setLanguageClick(tvChina, Language.CHINA)
        setLanguageClick(tvUSA, Language.USA)

        when (LanguageManager.getPreferLanguage(context)) {
            Language.TAIWAN.code -> setViewSelected(context, tvTaiwan)
            Language.CHINA.code -> setViewSelected(context, tvChina)
            Language.USA.code -> setViewSelected(context, tvUSA)
        }
    }

    private fun setLanguageClick(textView: TextView, language: Language) {
        textView.setOnClickListener {
            listener.onSelect(language)
            dismiss()
        }
    }

    private fun setViewSelected(context: Context, textView: TextView) {
        textView.setTextColor(Color.WHITE)
        textView.background = AppCompatResources.getDrawable(context, R.drawable.title_rounded_bg)
    }
}
