package com.taomish.app.android.farmsanta.farmer.fragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.ktx.Firebase
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.GetCropListTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.LoginViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_login.LoginFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


/* Fragment with mobile number and reference code */
@Suppress("UNCHECKED_CAST")
class LoginWithMobileFragment : FarmSantaBaseFragment() {

    private var mVerificationId: String? = null

    private val loginViewModel: LoginViewModel by activityViewModels()
    private var progressDialog: ProgressDialog? = null
    override fun init() {

    }

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedState: Bundle?
    ): View {
        val contextThemeWrapper: Context = ContextThemeWrapper(activity, R.style.AppThemeMaterial)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        return localInflater.inflate(
            R.layout.fragment_login_with_mobile_reference,
            container,
            false
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initData()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    LoginFragmentScreen(
                        loginViewModel = loginViewModel,
                        requestOtp = this@LoginWithMobileFragment::getOtpForPhoneNumber,
                        onSignUp = this@LoginWithMobileFragment::goToSignUpFragment,
                        onTerms = this@LoginWithMobileFragment::goToTermsAndConditions
                    )
                }
            }
        }
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        val args = LoginWithMobileFragmentArgs.fromBundle(
            arguments ?: Bundle()
        )
        if (loginViewModel.countryCode.value.isEmpty()) {
            loginViewModel.countryCode.postValue(args.phoneCode.toString())
        }

        fetchCropList()
    }

    private fun getOtpForPhoneNumber() {
        openMobileOtpFragment()
    }




    private fun goToFarmerRegistration(phoneNumber: String) {
        AppPrefs(requireContext()).countryCode = loginViewModel.countryCode.value
        fragmentChangeHelper?.onFragmentData(
            AppConstants.FragmentConstants.FRAGMENT_ADD_FARMER,
            phoneNumber
        )
    }

    /*Bottom sheet dialog to enter mobile otp*/
    private fun openMobileOtpFragment() {
        val appPrefs = AppPrefs(requireContext())
        appPrefs.phoneNumber = loginViewModel.phoneNumber.value
        appPrefs.countryCode = loginViewModel.countryCode.value.removePrefix("+")
        val holder = DataHolder.getInstance()
        holder.dataObject = arrayOf<Any>(mVerificationId.toString(), "resentToken")
        val phoneNumberWithoutPlus =
            loginViewModel.countryCode.value + loginViewModel.phoneNumber.value
        fragmentChangeHelper?.onFragmentData(
            AppConstants.FragmentConstants.LOGIN_TO_CONFIRM_OTP_FRAGMENT,
            "resentToken",
            mVerificationId,
            phoneNumberWithoutPlus
        )
    }

    private fun goToSignUpFragment() {
        AppPrefs(requireContext()).countryCode = loginViewModel.countryCode.value
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.FRAGMENT_SIGN_UP)
        }
    }

    private fun goToTermsAndConditions() {
        findNavController().navigate(
            WebViewFragmentDirections
                .actionToWebviewFragment().apply {
                    title = getString(R.string.terms_and_conditions)
                    url = "file:///android_asset/terms_n_conditions.html"
                }
        )
    }

    private fun fetchCropList() {
        val task = GetCropListTask()
        task.context = requireContext()
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    if (data.isNotEmpty()) {
                        val crops = data as ArrayList<CropMaster>?
                        if (!crops.isNullOrEmpty()) {
                            DataHolder.getInstance().cropArrayList = crops
                            DataHolder.getInstance().setCropMasterMap()
                        }
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }
}

