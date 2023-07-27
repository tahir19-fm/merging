package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SelectLanguageViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_select_language.SelectLanguageFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.utils.postValue

class SelectLanguageFragment : FarmSantaBaseFragment() {

    private val selectLanguageViewModel: SelectLanguageViewModel by activityViewModels()
    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root
        init()
        initData()
        binding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    SelectLanguageFragmentScreen(
                        viewModel = selectLanguageViewModel,
                        onNext = this@SelectLanguageFragment::goToLoginFragment
                    )
                }
            }
        }
        return root
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
        mContext = requireContext()
        selectLanguageViewModel.fetchTerritory(mContext)
        selectLanguageViewModel.fetchLanguages(mContext)
    }


    private fun goToLoginFragment(phoneCode: Int) {
        selectLanguageViewModel.setLanguageId(mContext)
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.FRAGMENT_SELECT_LANGUAGE_TO_LOGIN,
                phoneCode
            )
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        selectLanguageViewModel.fetchTerritory(mContext)
        selectLanguageViewModel.selectedTerritoryDto.postValue(null)
        selectLanguageViewModel.selectedCountry.postValue("")
    }
}