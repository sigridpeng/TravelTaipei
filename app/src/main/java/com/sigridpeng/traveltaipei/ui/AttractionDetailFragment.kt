package com.sigridpeng.traveltaipei.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.sigridpeng.traveltaipei.R
import com.sigridpeng.traveltaipei.adapter.ImageAdapter
import com.sigridpeng.traveltaipei.model.Attraction
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.indicator.RectangleIndicator

class AttractionDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_attraction_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ibBack = view.findViewById<ImageButton>(R.id.ib_back)
        val banner =
            view.findViewById<Banner<String, BannerAdapter<String, ImageAdapter.ImageViewHolder>>>(R.id.banner)
        val tvTitle = view.findViewById<TextView>(R.id.toolbarTitle)
        val tvOpenTime = view.findViewById<TextView>(R.id.tv_open_time)
        val tvTel = view.findViewById<TextView>(R.id.tv_tel)
        val tvOfficialSite = view.findViewById<TextView>(R.id.tv_official_site)
        val tvAddress = view.findViewById<TextView>(R.id.tv_address)
        val tvIntroduction = view.findViewById<TextView>(R.id.tv_introduction)

        val attraction = arguments?.getSerializable("ATTRACTION") as? Attraction
        if (attraction != null) {
            with(attraction) {
                tvTitle.text = name
                tvIntroduction.text = introduction
                tvOpenTime.text =
                    setStringContent(resources.getString(R.string.open_time), openTime)
                tvTel.text = setStringContent(resources.getString(R.string.tel), tel)
                tvOfficialSite.text =
                    setStringContent(resources.getString(R.string.official_site), officialSite)
                tvAddress.text = setStringContent(resources.getString(R.string.address), address)

                tvOfficialSite.setOnClickListener {
                    val webViewFragment = WebViewFragment.newInstance(officialSite, attraction.name)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, webViewFragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
            val srcList: List<String> = attraction.images.map { it.src }
            banner.setAdapter(ImageAdapter(srcList)).indicator =
                RectangleIndicator(requireContext())

        }

        ibBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }


    }

    private fun setStringContent(title: String, content: String): CharSequence {
        return StringBuilder().append(title).append(": ").append(content).toString()
    }
}