package com.sigridpeng.traveltaipei.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.addCallback
import com.sigridpeng.traveltaipei.R

private const val URL = "url"
private const val TITLE = "title"

class WebViewFragment : Fragment() {
    private var url: String? = null
    private var title: String? = null
    private lateinit var webView: WebView

    companion object {
        @JvmStatic
        fun newInstance(url: String, title: String?) =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(URL, url)
                    putString(TITLE, title)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL)
            title = it.getString(TITLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvTitle = view.findViewById<TextView>(R.id.toolbarTitle)
        val ibBack = view.findViewById<ImageButton>(R.id.ib_back)
        webView = view.findViewById(R.id.webView)
        val loadingView = view.findViewById<ProgressBar>(R.id.progress_bar_loading)
        val tvError = view.findViewById<TextView>(R.id.tv_error)

        webView.webViewClient = WebViewClient()

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        if (title != null) {
            tvTitle.text = title
        }
        if (url != null) {
            webView.loadUrl(url!!)
        }

        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(
                view: WebView?,
                url: String?,
                favicon: android.graphics.Bitmap?,
            ) {
                super.onPageStarted(view, url, favicon)
                loadingView.visibility = ProgressBar.VISIBLE
                tvError.visibility = TextView.GONE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loadingView.visibility = ProgressBar.GONE
            }

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?,
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                tvError.text = getString(R.string.failed_to_load_data)
                tvError.visibility = TextView.VISIBLE
                loadingView.visibility = ProgressBar.GONE
            }
        }

        ibBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                val fragmentManager = requireActivity().supportFragmentManager
                fragmentManager.popBackStack()
            }
        }
    }
}