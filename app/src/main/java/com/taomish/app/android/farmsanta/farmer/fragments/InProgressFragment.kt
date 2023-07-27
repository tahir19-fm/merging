package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.view.ContextThemeWrapper
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment

class InProgressFragment : FarmSantaBaseFragment() {
    private var imageViewBack: ImageView? = null
    override fun init() {}
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle
    ): View {
        val contextThemeWrapper: Context = ContextThemeWrapper(
            activity, R.style.AppThemeMaterial
        )
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        return localInflater.inflate(R.layout.fragment_in_progress, container, false)
    }

    override fun initViewsInLayout() {
        imageViewBack = initThisView(R.id.inProgress_img_back)
    }

    override fun initListeners() {
        imageViewBack!!.setOnClickListener { v: View? -> requireActivity().onBackPressed() }
    }

    override fun initData() {}

    companion object {
        private const val TAG = "skt"
    }
}