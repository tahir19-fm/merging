package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import android.content.Context
import android.os.CountDownTimer
import android.view.View
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.taomish.app.android.farmsanta.farmer.background.GetOtpTask
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer
import com.taomish.app.android.farmsanta.farmer.models.api.user.MobileOtp
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ConfirmOtpViewModel : ViewModel() {
    val mobileNo = mutableStateOf<String?>(null)
    val currentUser = mutableStateOf<Farmer?>(null)
    val hasResendToken = mutableStateOf(false)
    val enableResendToken = mutableStateOf(false)
    val countDownText = mutableStateOf("0:60")
    val isTimerOn = mutableStateOf(false)
    val otpDigits = List(6) { mutableStateOf("") }
    val a = MutableStateFlow("")


    fun resendToken(context: Context, view: View?) {
        if (enableResendToken.value && hasResendToken.value.not()) {

            hasResendToken.postValue(true)

            val task = GetOtpTask()
            task.context = context
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any?) {
                    if (data != null && data is MobileOtp) {
                        if (data.response) {
                            startTimer()
                            enableResendToken.postValue(false)
                        }
                    }
                }

                override fun onTaskFailure(reason: String?, errorMessage: String?) {
                    context.showToast("reason: $reason")
                }
            })
            task.setShowLoading(false)
            task.execute(mobileNo.value)
        }
    }

    fun startTimer() {
        isTimerOn.postValue(true)
        countDownText.postValue("0:60")
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                val text =
                    "in 0:" +
                            if (secondsRemaining > 9)
                                secondsRemaining
                            else
                                "0$secondsRemaining"
                countDownText.postValue(text)
            }

            override fun onFinish() {
                countDownText.postValue("")
                isTimerOn.postValue(false)
            }
        }.start()
    }

    fun validate(): Boolean {
        return otpDigits.all { it.value.isNotEmpty() }
    }

    fun getOtp(): String {
        var otp = ""
        otpDigits.forEach {
            otp += it.value
        }
        return otp
    }

    fun clearOtp() {
        otpDigits.forEach { it.postValue("") }
    }
}