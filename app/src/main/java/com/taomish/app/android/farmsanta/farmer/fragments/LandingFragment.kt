package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants

class LandingFragment : FarmSantaBaseFragment() {
    private lateinit var loginBtn: Button
    private lateinit var regBtn: Button
    override fun init() {

    }

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_landing, container, false)
    }

    override fun initViewsInLayout() {
        loginBtn = initThisView(R.id.login_btn)
        regBtn = initThisView(R.id.reg_btn)
    }

    override fun initListeners() {
        loginBtn.setOnClickListener { goToSelectLanguageFragment() }
        regBtn.setOnClickListener { goToSelectLanguageFragment() }
    }

    override fun initData() {
        val logoImageView: ImageView = initThisView(R.id.img_logo)

        Glide.with(this)
            .load(R.mipmap.fs_logo_o_w_tr_bg)
            .into(logoImageView)
    }

    private fun goToSelectLanguageFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.FRAGMENT_SELECT_LANGUAGE)
        }
    }

    private fun goToRegisterFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.FRAGMENT_LOGIN_MOBILE_NO)
        }
    }
}