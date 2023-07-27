package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.activities.MainActivity
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_sign_up.SignUpFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentSignUpBinding
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.utils.postValue


class SignUpFragment : FarmSantaBaseFragment() {

    private val viewModel: SignUpAndEditProfileViewModel by activityViewModels()

    private var mVerificationId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val root = binding.root
        initData()
        binding.signUpComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    SignUpFragmentScreen(
                        viewModel = viewModel,
                        requestOtp = { getOtpForPhoneNumber() },
                        onSignIn = { goToLoginFragment() },
                        onTerms = {
                            goToTermsAndConditions()
                        }
                    )
                }
            }
        }
        return root
    }

    private fun goToTermsAndConditions() {
        findNavController().navigate(WebViewFragmentDirections.actionToWebviewFragment().apply {
            title = getString(R.string.terms_and_conditions)
            url = "file:///android_asset/terms_n_conditions.html"
        })
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
        val prefs = AppPrefs(requireContext())
        viewModel.countryCode.postValue(prefs.countryCode)
    }

    private fun getOtpForPhoneNumber() {
        openMobileOtpFragment()
    }


    private fun goToFarmerRegistration(phoneNumber: String) {
        AppPrefs(requireContext()).countryName = viewModel.selectedTerritoryDto.value?.countryName
        fragmentChangeHelper?.onFragmentData(
            AppConstants.FragmentConstants.FRAGMENT_ADD_FARMER,
            phoneNumber
        )
    }


    private fun openMobileOtpFragment() {
        val appPrefs = AppPrefs(requireContext())
        appPrefs.phoneNumber = viewModel.mobileNumber.value
        appPrefs.countryCode = viewModel.countryCode.value
        val holder = DataHolder.getInstance()
        holder.dataObject = arrayOf<Any>(mVerificationId.toString(), "mobileNumber")
        fragmentChangeHelper?.onFragmentData(
            AppConstants.FragmentConstants.SIGN_UP_TO_CONFIRM_OTP_FRAGMENT,
            "mobileNumber",
            mVerificationId,
            "mobileNumber",
            true
        )
    }

    private fun goToLoginFragment() {
        (activity as MainActivity).navController.popBackStack()
    }
}