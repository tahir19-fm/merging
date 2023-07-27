package com.taomish.app.android.farmsanta.farmer.activities

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseActivity
import com.taomish.app.android.farmsanta.farmer.databinding.ActivityMain2Binding
import com.yarolegovich.slidingrootnav.SlideGravity
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder


class MainActivity2 : FarmSantaBaseActivity() {
    private lateinit var slidingRootNav: SlidingRootNav
    private var navController: NavController? = null
    private var appBarConfiguration: AppBarConfiguration? = null

    private val binding: ActivityMain2Binding by lazy {
        ActivityMain2Binding.inflate(layoutInflater)
    }

    override fun init() {
    }

    override fun initContentView(): View {
        return binding.root
    }

    override fun initUIElements() {
        setSupportActionBar(binding.toolbar)

        slidingRootNav = SlidingRootNavBuilder(this)
            .withDragDistance(220)
            .withRootViewScale(0.82f)
            .withToolbarMenuToggle(binding.toolbar)
            .withMenuOpened(false)
            .withGravity(SlideGravity.LEFT)
            .withContentClickableWhenMenuOpened(false)
            .withMenuLayout(R.layout.sliding_menu_layout)
            .inject()
    }

    override fun initListeners() {
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_home, R.id.navigation_pop,
            R.id.action_scouting_image_list, R.id.navigation_social_wall,
            R.id.navigation_profile
        ).build()
        navController = findNavController(this, R.id.nav_host_fragment_activity_main)
        navController?.addOnDestinationChangedListener { controller: NavController?, destination: NavDestination?, arguments: Bundle? ->
            onDestinationChange(
                destination!!
            )
        }
    }

    override fun initData() {
//        setupWithNavController(binding.toolbar, navController!!, appBarConfiguration!!)
        setupWithNavController(binding.bottomNavView, navController!!)
    }

    private fun onDestinationChange(destination: NavDestination) {
        when (destination.id) {
            R.id.navigation_profile, R.id.navigation_pop, R.id.navigation_social_wall, R.id.navigation_home -> {
                Handler().postDelayed({ binding.toolbar.setNavigationIcon(R.drawable.ic_drawer) }, 0)
                binding.textViewTitle.visibility = View.VISIBLE
                binding.appbar.visibility = View.VISIBLE
                binding.bottomNavView.visibility = View.VISIBLE
                setHeaderValue()
            }
            R.id.navigation_landing, R.id.navigation_onboard -> {
                binding.appbar.visibility = View.GONE
                hideBottomNavigation()
            }
            else -> {
                binding.textViewTitle.visibility = View.GONE
                hideBottomNavigation()
            }
        }
    }

    private fun setHeaderValue() {
        /*val header: View = navigationView.getHeaderView(0)
        val userNameTextView = header.findViewById<TextView>(R.id.drawerHeader_text_user)
        val phoneTextView = header.findViewById<TextView>(R.id.drawerHeader_text_phone)
        val riv = header.findViewById<RoundedImageView>(R.id.profile_riv_pic)
        val phoneNumber = "+" + appPrefs.getPhoneNumber()
        homeViewModel.getProfile().observe(this, Observer { farmer: Farmer ->
            Glide.with(this)
                .load(URLConstants.S3_PROFILE_IMAGE_BASE_URL + farmer.profileImage)
                .placeholder(R.mipmap.ic_avatar)
                .apply(RequestOptions().circleCrop())
                .into(riv)
            userNameTextView.text = String.format(
                "%s %s",
                farmer.firstName,
                farmer.lastName
            )
            phoneTextView.text = PhoneNumberUtils.formatNumber("+" + farmer.mobile, "IN")
        })
        userNameTextView.text =
            String.format("%s %s", appPrefs.getFirstName(), appPrefs.getLastName())*/
    }

    private fun hideBottomNavigation() {
        Handler().postDelayed({ binding.bottomNavView.visibility = View.GONE }, 200)
    }

}