package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.adapters.OnBoardingPagerAdapter
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import java.util.*

class OnBoardingFragment: FarmSantaBaseFragment() {

    private lateinit var viewPagerOnBoarding: ViewPager
    private lateinit var  tabLayoutOnBoardingDot: TabLayout

    private lateinit var  getStartedButton: Button
    override fun init() {
    }

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_on_boarding, container, false)
    }

    override fun initViewsInLayout() {
        viewPagerOnBoarding = initThisView(R.id.onBoarding_viewPager_screens)
        tabLayoutOnBoardingDot = initThisView(R.id.onBoarding_tabLayout_dots)

        getStartedButton = initThisView(R.id.onBoarding_button_getStarted)
    }

    override fun initListeners() {
        getStartedButton.setOnClickListener { goToLandingPage() }
    }

    override fun initData() {
        setAdapter()
    }

    private fun setAdapter() {
        val imageIds = ArrayList<Int>()
        val titles = ArrayList<String>()
        val descriptions = ArrayList<String>()
        imageIds.add(R.mipmap.img_onb_1)
        imageIds.add(R.mipmap.img_onb_2)
        imageIds.add(R.mipmap.img_onb_3)
        titles.add("Crop Information")
        titles.add("Agronomic Advisory")
        titles.add("Crop Price Information")
        descriptions.add(
            """
     Get access to diverse
     information on crops and
     cropping processes.
    """.trimIndent()
        )
        descriptions.add(
            """
    Expert agronomists to 
    guide and help you through
     the crop seasons.
    """.trimIndent()
        )
        descriptions.add(
            """
    Daily market price of your
     season crops.
    """.trimIndent()
        )
        val adapter = OnBoardingPagerAdapter(requireContext(), imageIds, titles, descriptions)
        viewPagerOnBoarding.adapter = adapter
        tabLayoutOnBoardingDot.setupWithViewPager(viewPagerOnBoarding, true)
    }

    private fun goToLandingPage() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.LANDING_FRAGMENT)
        }
    }
}