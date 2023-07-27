package com.taomish.app.android.farmsanta.farmer.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentSavedPopsBinding

@SuppressLint("MutableCollectionMutableState")
class SavedPopsFragment : FarmSantaBaseFragment() {



    override fun init() { }

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_saved_pops, container, false)
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {}

    companion object {
        const val ARG_OBJECT = "cropName"
    }
}