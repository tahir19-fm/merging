package com.taomish.app.android.farmsanta.farmer.baseclass;

import android.app.Application;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;
import com.taomish.app.android.farmsanta.farmer.BuildConfig;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.helper.AppSignatureHelper;

import java.util.Locale;


public class FarmsantaBaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (!Places.isInitialized()) {
            try {
                Places.initialize(getApplicationContext(), BuildConfig.MAPS_API_KEY, Locale.US);
            } catch (Exception e) {
                Toast.makeText(this, "map key is empty", Toast.LENGTH_LONG).show();
            }
        }
        new AppSignatureHelper(this).getAppSignatures();
    }
}
