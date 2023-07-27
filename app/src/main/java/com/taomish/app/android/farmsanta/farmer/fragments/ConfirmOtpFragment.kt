package com.taomish.app.android.farmsanta.farmer.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.activities.MainActivity
import com.taomish.app.android.farmsanta.farmer.background.AuthenticateUserTask
import com.taomish.app.android.farmsanta.farmer.background.GetCurrentFarmerTask
import com.taomish.app.android.farmsanta.farmer.background.GetOtpTask
import com.taomish.app.android.farmsanta.farmer.background.SubscribeToPushTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.broadcast.MySMSBroadCastReceiver
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.ConfirmOtpViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_confirm_otp.ConfirmOtpFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.controller.NavigationController
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentConfirmOtpBinding
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer
import com.taomish.app.android.farmsanta.farmer.models.api.login.User
import com.taomish.app.android.farmsanta.farmer.models.api.notification.Subscribe
import com.taomish.app.android.farmsanta.farmer.models.api.user.MobileOtp
import com.taomish.app.android.farmsanta.farmer.models.api.user.UserToken
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class ConfirmOtpFragment : FarmSantaBaseFragment() {

    private val viewModel: ConfirmOtpViewModel by activityViewModels()

    private var currentContext: Context? = null
    private lateinit var mySMSBroadCastReceiver: MySMSBroadCastReceiver
    private var isSigningUp: Boolean = false
    private lateinit var prefs: AppPrefs
    private var mVerificationId: String? = null
    private var phoneNumberForOtpString: String? = null
    private var progressDialog: ProgressDialog? = null
    private var isCameroon = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentConfirmOtpBinding.inflate(inflater, container, false)
        val view = binding.root
        initData()
        binding.confirmOtpComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    ConfirmOtpFragmentScreen(
                        confirmOtpViewModel = viewModel,
                        onAuthenticateOtp = {
                            viewModel.mobileNo.value?.let {
                                authenticateUserOtp(it)
                            }
                        },
                        onBackPressed = this@ConfirmOtpFragment::onBackPressed,
                        resendToken = this@ConfirmOtpFragment::resendToken
                    )
                }
            }
        }
        return view
    }

    override fun init() {}

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedState: Bundle?,
    ): View? {
        return null
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        currentContext = requireContext()
        prefs = AppPrefs(currentContext)
        val args = ConfirmOtpFragmentArgs.fromBundle(
            arguments ?: Bundle()
        )
        val appPrefs = AppPrefs(currentContext)
        isSigningUp = args.isSigningUp
        val regex = Regex("\\D")
        val phoneNumberWithoutPlus = regex.replace(
            appPrefs.countryCode + appPrefs.phoneNumber, ""
        )
        phoneNumberForOtpString = "+$phoneNumberWithoutPlus"
        viewModel.mobileNo.postValue(phoneNumberWithoutPlus)


        if (appPrefs.countryCode.contains("237")) {
            isCameroon = true
        }
        sendOtp()
    }


    private fun authenticateUserOtp(phone: String) {
        if (isCameroon) {
            val user = User()
            user.userName = phone
            user.password = viewModel.getOtp()
            val task = AuthenticateUserTask(user)
            task.context = requireContext()
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {
                    if (data is UserToken) {
                        if (data.token.isNullOrEmpty().not()) {
                            fetchUserDetails(data)
                        } else {
                            if (data.responseCode == 404) goToFarmerRegistration()
                        }
                    }
                }

                override fun onTaskFailure(reason: String?, errorMessage: String) {}
            })
            task.execute()

        } else {
            val credential =
                PhoneAuthProvider.getCredential(mVerificationId!!, viewModel.getOtp())
            Firebase.auth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        completeProcess(phone)

                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w("PHONEAUTH", "signInWithCredential:failure", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                        requireContext().showToast("Something went wrong ,Try again")
                        // Update UI
                    }

                }
        }
    }

    private fun completeProcess(phone: String) {
        Log.d("PHONEAUTH", "signInWithCredential:success")
        val user = com.taomish.app.android.farmsanta.farmer.models.api.login.User()
        val regex = Regex("\\D")
        val phoneNumberWithoutPlus = regex.replace(
            phone, ""
        )
        user.userName = phoneNumberWithoutPlus
        user.password = "1234567"

        val t1 = AuthenticateUserTask(user)
        t1.context = requireContext()
        t1.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is UserToken) {
                    if (data.token.isNullOrEmpty().not()) {
                        fetchUserDetails(data)
                    } else {
                        if (data.responseCode == 404) goToFarmerRegistration()
                        else {
                            requireContext().showToast(R.string.something_went_wrong)
                        }
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String) {
                Log.w("PHONEAUTH", "failed:failure$errorMessage")

            }
        })
        t1.execute()
    }

    private fun fetchUserDetails(token: UserToken) {
        prefs.userToken = token.token
        prefs.refreshToken = token.refreshToken

        val task = GetCurrentFarmerTask()
        task.context = currentContext
        task.setShowLoading(true)
        task.setLoadingMessage("Getting user details")
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Farmer) {
                    viewModel.currentUser.postValue(data)
                    DataHolder.getInstance().selectedFarmer = data
                    sendFCMTokenToServer()
                    goToWelcomeFragment(token)
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute(token.token)
    }

    private fun goToWelcomeFragment(token: UserToken) {
        val appPrefs = AppPrefs(currentContext)
        appPrefs.userToken = token.token
        appPrefs.refreshToken = token.refreshToken
        appPrefs.isUserProfileCompleted = true

        NavigationController.getInstance(activity)
            .onFragmentChange(AppConstants.FragmentConstants.WELCOME_FARMER_FRAGMENT)
    }

    private fun onBackPressed() {
        (activity as MainActivity).navController.popBackStack()
    }

    private fun goToFarmerRegistration() {
        findNavController().navigate(R.id.action_confirmOtp_to_add_farmer)
    }

    private fun startSMSRetrieverClient() {
        val client = SmsRetriever.getClient(requireContext())
        val task = client.startSmsRetriever()
        task.addOnSuccessListener {
            Log.d("PHONEAUTH", "onCodeSent:success")
//            viewModel.mobileNo.value?.let {
//                completeProcess(it)
//            }

            registerBroadCastReceiver()
        }
        task.addOnFailureListener {
            Log.d("PHONEAUTH", "onCodeSent:failed ${it.localizedMessage}")
            Log.e("Otp read failed", "reason: ${it.localizedMessage}")
        }
    }

    private fun registerBroadCastReceiver() {
        Log.d("PHONEAUTH", "registerBroadCastReceiver: called")
        try {
            mySMSBroadCastReceiver = MySMSBroadCastReceiver()
            requireActivity().registerReceiver(
                mySMSBroadCastReceiver, IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
            )

            mySMSBroadCastReceiver.onOtpTimeOut = {
                Log.d("PHONEAUTH", "registerBroadCastReceiver: on otp timeout")
            }
            mySMSBroadCastReceiver.onOtpReceived = { otp ->
                Log.d("PHONEAUTH", "registerBroadCastReceiver: on otp received $otp")
                if (!otp.isNullOrEmpty() && otp.length == 6) {
                    otp.forEachIndexed { index, c ->
                        viewModel.otpDigits[index].postValue(c.toString())
                    }
                    if (viewModel.validate()) {
                        viewModel.mobileNo.value?.let {
                            authenticateUserOtp(it)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("PHONEAUTH", "registerBroadCastReceiver: on otp received $e")
        }
    }

    //    private fun getOtpFromMessage(message: String) {
//        // This will match any 6 digit number in the message
//        val pattern: Pattern = Pattern.compile("(|^)\\d{6}")
//        val matcher: Matcher = pattern.matcher(message)
//        if (matcher.find()) {
//            otpText.setText(matcher.group(0))
//        }
//    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("PHONEAUTH", "onCodeSent:succes acitivits $resultCode")
    }


    private fun resendToken() {
        if (viewModel.enableResendToken.value) {
            viewModel.hasResendToken.postValue(true)
            sendOtp()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        startSMSRetrieverClient()
    }


    private fun sendFCMTokenToServer() {
        val subscribe = Subscribe()
        subscribe.tokens = listOf(prefs.firebaseToken ?: "")
        val task = SubscribeToPushTask(subscribe)
        task.context = currentContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is Boolean) {
                    prefs.isTokenSent = data
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                prefs.isTokenSent = false
            }
        })
        task.execute()
    }


    private fun showSnackBar(message: String) {
        try {
//            view?.let {
//                Snackbar.make(
//                    requireContext(),
//                    it,
//                    message,
//                    Snackbar.LENGTH_INDEFINITE
//                ).apply {
//                    setBackgroundTint(Color.Black.toArgb())
//                    setTextColor(Color.White.toArgb())
//                    show()
//                    CoroutineScope(Dispatchers.Main).launch {
//                        delay(10000)
//                        dismiss()
//                    }
//                }
//            }

            view?.let {
                val snackbar = Snackbar.make(
                    it,
                    message,
                    Snackbar.LENGTH_INDEFINITE
                ).apply {
                    setBackgroundTint(Color.Black.toArgb())
                    setTextColor(Color.White.toArgb())
//                    show()
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(10000)
                        dismiss()
                    }
                }
                val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
                val params = snackbarLayout.layoutParams as FrameLayout.LayoutParams
                params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
//                snackbarLayout.apply { layoutParams = params }
                snackbar.view.apply { layoutParams = params }
                snackbar.show()
            }
        } catch (_: Exception) {
        }
    }


    private fun sendOtp() {
        if (isCameroon) {
            val regex = Regex("\\D")
            val phoneNumberWithoutPlus = regex.replace(
                phoneNumberForOtpString!!,
                ""
            )

            val task = GetOtpTask()
            task.context = requireContext()
            task.setShowLoading(false)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any?) {
                    if (data != null && data is MobileOtp) {
                        if (data.response) {
                            showSnackBar("OTP : " + data.otp)
                        } else if (data.responseCode == 404) {
                            goToFarmerRegistration()
                        }
                    }
                }

                override fun onTaskFailure(reason: String, errorMessage: String) {
                    showToast("failed to get otp\n reason: $reason\nerror: $errorMessage")
                }
            })
            task.execute(phoneNumberWithoutPlus)

        } else {
            try {
                Log.d("PHONEAUTH", "phone number is :$phoneNumberForOtpString")
            } catch (e: Exception) {

                Log.d("PHONEAUTH", "phone number is :$e")
            }
            val options = PhoneAuthOptions.newBuilder(Firebase.auth)
                .setPhoneNumber(phoneNumberForOtpString!!) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(requireActivity()) // Activity (for callback binding)
                .setCallbacks(callbacks)// OnVerificationStateChangedCallbacks

                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
            try {
                progressDialog = ProgressDialog(context)
                progressDialog!!.setMessage("Loading...")
                progressDialog!!.setCancelable(false)
                progressDialog!!.show()
            } catch (_: Exception) {
            }
        }
    }


    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            Log.d("PHONEAUTH", "onVerificationCompleted:")
            Log.d("PHONEAUTH", "onVerificationCompleted:$credential")
            try {
                progressDialog!!.dismiss()
            } catch (_: Exception) {
            }
            val otp = credential.smsCode.toString()
//            showSnackBar("OTP Is $otp")
            if (otp.isNotEmpty() && otp.length == 6) {
                otp.forEachIndexed { index, c ->
                    viewModel.otpDigits[index].postValue(c.toString())
                }
                if (viewModel.validate()) {
                    viewModel.mobileNo.value?.let {
                        authenticateUserOtp(it)
                    }
                }
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            try {
                progressDialog!!.dismiss()
            } catch (_: Exception) {

            }
            viewModel.startTimer()
            viewModel.enableResendToken.postValue(false)
            Log.w("PHONEAUTH", "onVerificationFailed", e)
            when (e) {
                is FirebaseAuthInvalidCredentialsException -> {
                }

                is FirebaseTooManyRequestsException -> {
                }
            }

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            Log.d("PHONEAUTH", "onCodeSent:$verificationId")
            mVerificationId = verificationId
            viewModel.startTimer()
            viewModel.enableResendToken.postValue(false)
            try {
                progressDialog!!.dismiss()
            } catch (_: Exception) {

            }


//            val client = SmsRetriever.getClient(requireActivity())
//
//            val task: Task<Void> = client.startSmsRetriever()
//
//            task.addOnSuccessListener(OnSuccessListener<Void?> {
//                Log.d("PHONEAUTH", "onCodeSent:success")
////                viewModel.mobileNo.value?.let {
////                    completeProcess(it)
////                }
//            })
//
//            task.addOnFailureListener(OnFailureListener {
//                Log.d("PHONEAUTH", "onCodeSent:failure")
//            })

            startSMSRetrieverClient()

        }

        override fun onCodeAutoRetrievalTimeOut(p0: String) {
            super.onCodeAutoRetrievalTimeOut(p0)
            try {
                progressDialog!!.dismiss()
            } catch (_: Exception) {

            }
            Log.d("PHONEAUTH", "auto failed $p0")
        }
    }

    override fun onDestroy() {
        viewModel.clearOtp()
        super.onDestroy()
    }
}