package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.adapters.NewsAllRecyclerAdapter
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.home.News

class NewsAllFragment : FarmSantaBaseFragment() {
    private var recyclerViewAllNews: RecyclerView? = null
    private var newsArrayList: ArrayList<News>? = null
    override fun init() {}
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle
    ): View {
        return inflater.inflate(R.layout.fragment_news_all, container, false)
    }

    override fun initViewsInLayout() {
        recyclerViewAllNews = initThisView(R.id.newsAll_recycler_news)
    }

    override fun initListeners() {}
    override fun initData() {
        loadAllNews()
    }

    private fun loadAllNews() {
        newsArrayList = DataHolder.getInstance().newsArrayList
        if (newsArrayList != null && newsArrayList!!.size > 0) {
            val adapter = NewsAllRecyclerAdapter(newsArrayList!!, requireContext())
            val layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            recyclerViewAllNews!!.layoutManager = layoutManager
            recyclerViewAllNews!!.itemAnimator = DefaultItemAnimator()
            adapter.setItemClickListener { view: View?, tag: String?, index: Int ->
                openBrowser(
                    index
                )
            }
            recyclerViewAllNews!!.adapter = adapter
        }
    }

    private fun openBrowser(position: Int) {
        val (_, _, _, _, _, _, url) = newsArrayList!![position]
        val uri = Uri.parse(url) // missing 'http://' will cause crashed
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}