package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.fragment.findNavController
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_welcome_farm_santa.WelcomeFarmSantaFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentWelcomeFarmSantaBinding

class WelcomeFarmSantaFragment : FarmSantaBaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentWelcomeFarmSantaBinding.inflate(inflater, container, false)
        val root = binding.root
        init()
        initData()
        binding.welcomeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    WelcomeFarmSantaFragmentScreen()
                }
            }
        }
        return root
    }

    private fun goToHomeFragment() {
        findNavController().navigate(WelcomeFarmSantaFragmentDirections.actionWelcomeFarmSantaFragmentToNavigationHome())
        /*if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.HOME_FRAGMENT)
        }*/
    }

    override fun init() {}

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedState: Bundle?
    ): View? {
        return null
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        object : CountDownTimer(2000, 2000) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                goToHomeFragment()
            }

        }.start()
    }
}