package com.sigridpeng.traveltaipei.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.sigridpeng.traveltaipei.LanguageManager
import com.sigridpeng.traveltaipei.R
import com.sigridpeng.traveltaipei.adapter.AttractionAdapter
import com.sigridpeng.traveltaipei.adapter.NewsAdapter
import com.sigridpeng.traveltaipei.model.Attraction
import com.sigridpeng.traveltaipei.model.Language
import com.sigridpeng.traveltaipei.model.News
import com.sigridpeng.traveltaipei.network.api.RetrofitHelper
import com.sigridpeng.traveltaipei.network.repository.AttractionRepository
import com.sigridpeng.traveltaipei.network.repository.NewsRepository
import com.sigridpeng.traveltaipei.ui.dialog.LanguageDialog
import com.sigridpeng.traveltaipei.viewmodel.AttractionViewModel
import com.sigridpeng.traveltaipei.viewmodel.NewsViewModel
import com.sigridpeng.traveltaipei.viewmodel.factory.AttractionViewModelFactory
import com.sigridpeng.traveltaipei.viewmodel.factory.NewsViewModelFactory
import java.util.Locale


class HomeFragment : Fragment(), NewsAdapter.OnItemClickListener,
    AttractionAdapter.OnItemClickListener, LanguageDialog.OnSelectListener {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var attractionViewModel: AttractionViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var attractionAdapter: AttractionAdapter

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ibLanguage = view.findViewById<ImageButton>(R.id.ib_language)
        val tvNewsState = view.findViewById<TextView>(R.id.tv_news_state)
        val tvAttractionState = view.findViewById<TextView>(R.id.tv_attraction_state)
        val rvNews = view.findViewById<RecyclerView>(R.id.rv_news)
        val rvAttraction = view.findViewById<RecyclerView>(R.id.rv_attraction)
        val loadingView = view.findViewById<ProgressBar>(R.id.progress_bar_loading)
        val refreshLayout: RefreshLayout = view.findViewById(R.id.refreshLayout)
        val apiService = RetrofitHelper.getInstance()
        val newsRepository = NewsRepository(apiService)
        val attractionRepository = AttractionRepository(apiService)

        newsAdapter = NewsAdapter(emptyList(), this)
        rvNews.adapter = newsAdapter
        attractionAdapter = AttractionAdapter(emptyList(), this)
        rvAttraction.adapter = attractionAdapter

        newsViewModel = ViewModelProvider(
            requireActivity(),
            NewsViewModelFactory(requireContext(), newsRepository)
        )[NewsViewModel::class.java]

        attractionViewModel = ViewModelProvider(
            requireActivity(),
            AttractionViewModelFactory(requireContext(), attractionRepository)
        )[AttractionViewModel::class.java]

        ibLanguage.setOnClickListener {
            val languageDialog = context?.let { context -> LanguageDialog(context, this) }
            languageDialog?.show()
        }

        with(newsViewModel) {
            newsList.observe(requireActivity()) { newsList ->
                newsAdapter = NewsAdapter(newsList, this@HomeFragment)
                rvNews.adapter = newsAdapter
                tvNewsState.visibility = View.GONE
            }
            setLoadingView(isLoading, isError, this@HomeFragment, loadingView, tvNewsState)
        }

        with(attractionViewModel) {
            attractionList.observe(requireActivity()) { attractionList ->
                attractionAdapter = AttractionAdapter(attractionList, this@HomeFragment)
                rvAttraction.adapter = attractionAdapter
                tvAttractionState.visibility = View.GONE
            }
            setLoadingView(isLoading, isError, this@HomeFragment, loadingView, tvAttractionState)
        }
        refreshLayout.setOnRefreshListener {
            newsViewModel.refreshData(requireContext())
            attractionViewModel.refreshData(requireContext())
            it.finishRefresh(2000 )
        }
    }

    override fun onItemClick(news: News) {
        val webViewFragment = WebViewFragment.newInstance(news.url, null)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, webViewFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onItemClick(attraction: Attraction) {
        val bundle = Bundle()
        bundle.putSerializable("ATTRACTION", attraction)

        val attractionDetailFragment = AttractionDetailFragment()
        attractionDetailFragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, attractionDetailFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onSelect(language: Language) {
        val locale = when (language.code) {
            Language.TAIWAN.code -> Locale.TAIWAN
            Language.CHINA.code -> Locale.CHINA
            Language.USA.code -> Locale.ENGLISH
            else -> Locale.ENGLISH
        }
        changeLocale(locale)
        setAppLanguage(locale)
    }

    private fun setAppLanguage(locale: Locale) {
        LanguageManager.setAppLanguage(requireActivity().applicationContext, locale)
        requireActivity().recreate()

    }

    private fun changeLocale(locale: Locale) {
        newsViewModel.changeLocale(locale)
        attractionViewModel.changeLocale(locale)
    }

    private fun setLoadingView(
        isLoadingState: LiveData<Boolean>,
        isErrorState: LiveData<Boolean>,
        lifecycleOwner: LifecycleOwner,
        loadingView: View,
        stateView: TextView
    ) {
        isLoadingState.observe(lifecycleOwner) { isLoading ->
            if (isLoading) {
                loadingView.visibility = View.VISIBLE
            } else {
                loadingView.visibility = View.GONE
            }
        }
        isErrorState.observe(lifecycleOwner) { isError ->
            if (isError) {
                stateView.visibility = View.VISIBLE
                stateView.text = getString(R.string.failed_to_load_data)
                stateView.setTextColor(Color.RED)
            } else {
                stateView.text = getString(R.string.no_data)
                stateView.setTextColor(Color.BLACK)
            }
        }
    }
}

