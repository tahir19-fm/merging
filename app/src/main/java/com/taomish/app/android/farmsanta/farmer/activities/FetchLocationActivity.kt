package com.taomish.app.android.farmsanta.farmer.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.taomish.app.android.farmsanta.farmer.BuildConfig
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.models.view_model.LocationListener
import com.taomish.app.android.farmsanta.farmer.models.view_model.LocationLiveData
import com.taomish.app.android.farmsanta.farmer.models.view_model.LocationRequestResult

open class FetchLocationActivity : AppCompatActivity(), LocationListener {
    private lateinit var requestLocationPermissions: ActivityResultLauncher<Array<String>>
    private lateinit var requestForGps: ActivityResultLauncher<IntentSenderRequest>
    private lateinit var locationData: LocationLiveData
    private var snackBar: Snackbar? = null
    private var isGpsOrPermissionRejectedByUser = false
    var requestIfGpsRejectedByUser = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationData = LocationLiveData(this)
        initLocationPermissions(
            locationData,
            this
        )
        initLocationListener(
            locationData,
            this
        )
        initGpsRequest(
            locationData
        )
    }

    fun requestForLocation() {
        when {
            isGpsOrPermissionRejectedByUser.not() -> {
                startLocationRequest()
            }
            isGpsOrPermissionRejectedByUser && requestIfGpsRejectedByUser -> {
                startLocationRequest()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        stopLocationRequest()
    }

    private fun initGpsRequest(locationData: LocationLiveData) {
        requestForGps = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                locationData.onGpsTurnOn(true)
            } else {
                isGpsOrPermissionRejectedByUser = true
                locationData.onGpsTurnOn(false)
            }
        }
    }

    private fun initLocationPermissions(
        locationData: LocationLiveData,
        locationListener: LocationListener
    ) {
        requestLocationPermissions = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val isGranted = permissions.all {
                it.value
            }
            if (isGranted) {
                locationData.onPermissionGranted()
            } else {
                locationListener.onLocationPermissionDeny()
            }
        }
    }

    private fun initLocationListener(
        locationData: LocationLiveData,
        locationListener: LocationListener
    ) {
        locationData.observe(this) {
            when (it) {
                is LocationRequestResult.LocationData -> {
                    Log.v("Location Result", "-----> $it")
                    hideSnackBar()
                    locationListener.onLocationAvailable(it)
                }
                is LocationRequestResult.LocationAccessFailure -> {
                    Log.v("Location Failure", "-----> $it")
                    hideSnackBar()
                    locationListener.onLocationFailure()
                    isGpsOrPermissionRejectedByUser = true
                }
                is LocationRequestResult.LocationResolvableException -> {
                    Log.v("Location Gps Required", "-----> $it")
                    hideSnackBar()
                    requestForGps.launch(
                        IntentSenderRequest.Builder(it.exception.resolution).build()
                    )
                }
                is LocationRequestResult.LocationPermission -> {
                    Log.v("Permission Required", "-----> $it")
                    hideSnackBar()
                    requestForLocationPermission()
                }
                is LocationRequestResult.LocationInProgress -> {
                    Log.v("Location Is In Progress", "-----> $it")
                    showSnackBar(
                        getString(R.string.searching_location_msg),
                        Snackbar.LENGTH_INDEFINITE
                    )
                }
                else -> {}
            }
        }
    }

    private fun requestForLocationPermission() {
        requestLocationPermissions.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun stopLocationUpdates(locationData: LocationLiveData) {
        locationData.removeLocationUpdates()
    }

    private fun startLocationUpdates(locationData: LocationLiveData) {
        locationData.startLocationUpdates()
    }

    private fun startLocationRequest() {
        startLocationUpdates(locationData)
    }

    fun stopLocationRequest() {
        stopLocationUpdates(locationData)
    }

    @SuppressLint("RestrictedApi")
    private fun showSnackBar(
        message: String,
        duration: Int = Snackbar.LENGTH_LONG
    ) {
        val view: View = window.decorView.findViewById(android.R.id.content)
        snackBar = Snackbar.make(
            view,
            message,
            duration
        )
        val snackBarView: ViewGroup = snackBar?.view as ViewGroup
        val snackBarContentLayout: SnackbarContentLayout =
            snackBarView.getChildAt(0) as SnackbarContentLayout
        val textView = snackBarContentLayout.messageView
        textView.maxLines = 5
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackBar?.show()
    }

    private fun hideSnackBar() {
        snackBar?.dismiss()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onLocationPermissionDeny() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            alertDialog(
                title = getString(R.string.permission_required),
                message = getString(R.string.perm_img_location_deny_message),
                btnOneText = getString(R.string.ok),
                cancelable = false,
                btnTwoText = "",
                function = {
                    requestForLocationPermission()
                }
            )
        } else {
            locationData.permissionDenied = true
            alertDialog(
                title = getString(R.string.permission_required),
                message = getString(R.string.msg_location_settings),
                btnOneText = getString(R.string.ok),
                btnTwoText = getString(R.string.cancel),
                cancelable = false,
                function = {
                    if (it == Activity.RESULT_OK) {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        intent.data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                        startActivity(intent)
                        onLocationSettings()
                    } else {
                        onLocationFailure()
                    }
                }
            )
        }
    }

    override fun onLocationSettings() {
        super.onLocationSettings()
        locationData.clearFields()
    }

    fun alertDialog(
        message: String,
        btnOneText: String = getString(R.string.yes),
        btnTwoText: String = getString(R.string.no),
        function: (Int) -> Unit,
        cancelable: Boolean = true,
        title: String? = getString(R.string.app_name),
        isSpanned: Boolean = false
    ) {
        try {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(title ?: getString(R.string.app_name))
            builder.setMessage(message)
            builder.setPositiveButton(btnOneText) { dialog, _ ->
                function.invoke(Activity.RESULT_OK)
                dialog?.dismiss()
            }
            if (btnTwoText.isNotEmpty()) {
                builder.setNegativeButton(btnTwoText) { dialog, _ ->
                    function.invoke(Activity.RESULT_CANCELED)
                    dialog?.dismiss()
                }
            }
            val dialog = builder.create()
            dialog.setOnShowListener {
                dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(ContextCompat.getColor(this, R.color.grey_text))
            }
            dialog.setCancelable(cancelable)
            dialog.setCanceledOnTouchOutside(cancelable)
            dialog.show()
        } catch (e: Exception) {

        }
    }
}