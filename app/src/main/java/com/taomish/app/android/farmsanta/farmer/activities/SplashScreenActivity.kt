package com.taomish.app.android.farmsanta.farmer.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.messaging.FirebaseMessaging
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.db.ClearAllCropRecommendationTask
import com.taomish.app.android.farmsanta.farmer.constants.ApiConstants
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants.TAG
import com.taomish.app.android.farmsanta.farmer.databinding.ActivitySplashBinding
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var appUpdateManager: AppUpdateManager
    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater, null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeElementsAndLoadNextPage()
        appUpdateManager = AppUpdateManagerFactory.create(this)
        appUpdateManager.registerListener(listener)
        checkForUpdate()
    }

    private fun initializeElementsAndLoadNextPage() {

        lifecycleScope.launchWhenResumed {
            delay(300)
//            binding.backgroundIV.visibility = View.VISIBLE
            binding.farmSantaLabel.visibility = View.VISIBLE
        }

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                lifecycleScope.launch {
                    delay(600)
                    clearTables()
                }
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
                AppPrefs(this).firebaseToken = token

                // Log and toast
                val msg = getString(R.string.msg_token_fmt, token)
                Log.e(TAG, "Got firebase token -> $msg")
            })
    }


    private fun checkForUpdate() {
        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                // This example applies an immediate update. To apply a flexible update
                // instead, pass in AppUpdateType.FLEXIBLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    this,
                    MY_REQUEST_CODE
                )
            }
        }
    }


    private val listener: InstallStateUpdatedListener =
        InstallStateUpdatedListener { installState ->
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                // After the update is downloaded, show a notification
                // and request user confirmation to restart the app.
                showToast(R.string.update_downloaded_successfully)
                appUpdateManager.completeUpdate()
            }
        }


    private fun clearTables() {
        val task = ClearAllCropRecommendationTask()
        task.context = this
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                loadMainPage()
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                Toast.makeText(
                    this@SplashScreenActivity,
                    "Could not clear your data. Please try after some time",
                    Toast.LENGTH_LONG
                ).show()
                loadMainPage()
            }
        })
        task.setLoadingMessage("Clearing data")
        task.setShowLoading(false)
        task.execute()
    }

    private fun loadMainPage() {
        DataHolder.clearInstance()
        val startIntent = Intent(this, MainActivity::class.java)
        val bundle = intent?.extras
        bundle?.keySet()?.forEach {
            if (it.equals(ApiConstants.Notification.TYPE, true)) {
                startIntent.putExtra(ApiConstants.Notification.TYPE, bundle[it].toString())
            } else if (it.equals(ApiConstants.Notification.UUID, true)) {
                startIntent.putExtra(ApiConstants.Notification.UUID, bundle[it].toString())
            } else if (it.equals(ApiConstants.Notification.POST, true)) {
                startIntent.putExtra(ApiConstants.Notification.POST, bundle[it].toString())
            } else if (it.equals(ApiConstants.Notification.LANGUAGE_ID, true)) {
                startIntent.putExtra(ApiConstants.Notification.LANGUAGE_ID, bundle[it].toString())
            }
        }
        startActivity(startIntent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        appUpdateManager.unregisterListener(listener)
    }

    companion object {
        const val MY_REQUEST_CODE = 11
    }
}