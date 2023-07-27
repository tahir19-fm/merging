package com.taomish.app.android.farmsanta.farmer.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import java.util.regex.Pattern

@Suppress("DEPRECATION")
class MySMSBroadCastReceiver : BroadcastReceiver() {
    var onOtpReceived = { _: String? -> }
    var onOtpTimeOut: () -> Unit = { }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("PHONEAUTH", "onReceive: broadcast received ${intent?.action} ${SmsRetriever.SMS_RETRIEVED_ACTION}")
        when (intent?.action) {
            SmsRetriever.SMS_RETRIEVED_ACTION -> {
                Log.d("PHONEAUTH", "onReceive: broadcast received}")
                intent.extras?.let { bundle ->
                    val status = bundle.get(SmsRetriever.EXTRA_STATUS)
                    Log.d("PHONEAUTH", "onReceive: broadcast received $status}")
                    if (status is Status) {
                        when (status.statusCode) {
                            CommonStatusCodes.SUCCESS -> {
                                val message = bundle.getString(SmsRetriever.EXTRA_SMS_MESSAGE)
                                Log.d("PHONEAUTH", "onReceive: otp received ${message}")
                                if (!message.isNullOrEmpty()) {
                                    val pattern = Pattern.compile("(\\d{6})")
                                    val matcher = pattern.matcher(message)
                                    if (matcher.find()) {
                                        onOtpReceived.invoke(matcher.group(0))
                                    }
                                }
                            }

                            CommonStatusCodes.TIMEOUT -> {
                                onOtpTimeOut.invoke()
                            }
                        }
                    }

                }
            }
        }
    }
}