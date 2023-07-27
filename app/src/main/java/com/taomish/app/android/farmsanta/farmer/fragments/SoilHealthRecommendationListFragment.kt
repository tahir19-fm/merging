package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.adapters.CropRecommendationsAdapter
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.interfaces.OnRecyclerItemClickListener
import com.taomish.app.android.farmsanta.farmer.models.api.soil.CropRecommendation
import androidx.recyclerview.widget.DividerItemDecoration
import com.taomish.app.android.farmsanta.farmer.controller.NavigationController

class SoilHealthRecommendationListFragment(private val cropRecommendations: List<CropRecommendation>?) :
    FarmSantaBaseFragment() {
    private lateinit var currentContext:Context

    private lateinit var recommendationList: RecyclerView

    override fun init() {

    }

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_crop_recommendation_list, container, false)
    }

    override fun initViewsInLayout() {
        recommendationList                      = initThisView(R.id.recommendations_recycler_all)
    }

    override fun initListeners() {

    }

    override fun initData() {
        currentContext                          = requireContext()

        loadAllCropRecommendationName()
    }

    private fun loadAllCropRecommendationName() {
        cropRecommendations?.let {
            val adapter                         = CropRecommendationsAdapter(it, requireContext())
            adapter.setItemClickListener { _: View?, _: String?, index: Int ->
                Log.d(AppConstants.TAG, "Index is $index")
            }
            val layoutManager                   = LinearLayoutManager(context)
            recommendationList.layoutManager    = layoutManager
            recommendationList.itemAnimator     = DefaultItemAnimator()
            recommendationList.addItemDecoration(
                DividerItemDecoration(
                    currentContext, DividerItemDecoration.VERTICAL
                )
            )

            recommendationList.adapter          = adapter
        }
    }
}