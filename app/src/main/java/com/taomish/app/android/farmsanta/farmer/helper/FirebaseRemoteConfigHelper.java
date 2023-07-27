package com.taomish.app.android.farmsanta.farmer.helper;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants;
import com.taomish.app.android.farmsanta.farmer.interfaces.OnCompleteListener;

import java.util.List;

public class FirebaseRemoteConfigHelper {
    private static final String TAG = "skt";
    private final Activity activity;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private OnCompleteListener onCompleteListener;

    public FirebaseRemoteConfigHelper(Activity activity) {
        this.activity = activity;
    }

    public void fetchValues(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
        initFirebaseRemoteConfig();
    }

    private void initFirebaseRemoteConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Config params updated: ");
                        Log.d(TAG, "Fetch and activate succeeded");

                        getRemoteConfigVals();

                    } else {
                        Log.d(TAG, "Fetch failed " + task.getException());
                        if (onCompleteListener != null) {
                            onCompleteListener.onTaskComplete(false);
                        }
                    }
                });
    }

    private void getRemoteConfigVals() {
        long currentVersion = 0;

        PackageManager manager = activity.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(activity.getPackageName(), PackageManager.GET_ACTIVITIES);
            currentVersion = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String genderListString = mFirebaseRemoteConfig.getString("gender");
        String chatUrl = mFirebaseRemoteConfig.getString("CHAT_ENDPOINT");

        String getFarmerProfileUrl = mFirebaseRemoteConfig.getString("GET_PROFILE");
        String popForGroupUrl = mFirebaseRemoteConfig.getString("POP_FOR_GROUP");
        String seedRateCropsUrl = mFirebaseRemoteConfig.getString("SEED_RATE_CROPS");
        String socialMessagesUrl = mFirebaseRemoteConfig.getString("SOCIAL_MESSAGES");
        String cropGroupUrl = mFirebaseRemoteConfig.getString("CROP_GROUP");
        String myCropGroupUrl = mFirebaseRemoteConfig.getString("MY_CROP_POP");
        String newsFeedUrl = mFirebaseRemoteConfig.getString("NEWS_FEED");
        String seedRateUrl = mFirebaseRemoteConfig.getString("SEED_RATE");
        String userBookmarkUrl = mFirebaseRemoteConfig.getString("USER_BOOKMARK");
        String popSearchUrl = mFirebaseRemoteConfig.getString("POP_SEARCH");
        String popRecommendationUrl = mFirebaseRemoteConfig.getString("POP_RECOMMENDATION");
        String cropUrl = mFirebaseRemoteConfig.getString("CROP");
        String socialPostUrl = mFirebaseRemoteConfig.getString("SOCIAL_POST");
        String referenceDataUrl = mFirebaseRemoteConfig.getString("REFERENCE_DATA");
        String uploadPicUrl = mFirebaseRemoteConfig.getString("UPLOAD_PROFILE_PIC");

        String prodUrl = mFirebaseRemoteConfig.getString("PROD_URL");
        String testUrl = mFirebaseRemoteConfig.getString("DEV_BASE_URL");

        long currentProdVersion = mFirebaseRemoteConfig.getLong("PROD_VERSION");

        /**
         * * Changing the BASE URL. Not used
         // if (currentProdVersion == currentVersion) {
         //     URLConstants.BASE_URL               = prodUrl;
         // } else {
         //     Log.d(TAG, "Dev url = " + testUrl);
         //     URLConstants.BASE_URL               = testUrl;//"https://api.farmsanta.com/";
         // }
         */

        //URLConstants.BASE_URL                       = "https://api.farmsanta.com/";
        URLConstants.SOCIAL_POST = URLConstants.BASE_URL + socialPostUrl;
        URLConstants.CROP = URLConstants.BASE_URL + cropUrl;
        URLConstants.GET_POP_RECOMMENDATION = URLConstants.BASE_URL + popRecommendationUrl;
        URLConstants.POP_SEARCH = URLConstants.BASE_URL + popSearchUrl;
        URLConstants.USER_BOOKMARK = URLConstants.BASE_URL + userBookmarkUrl;
        URLConstants.SEED_RATE = URLConstants.BASE_URL + seedRateUrl;
        URLConstants.NEWS_FEED = URLConstants.BASE_URL + newsFeedUrl;
        URLConstants.MY_CROP_POP = URLConstants.BASE_URL + myCropGroupUrl;
        URLConstants.CROP_GROUP = URLConstants.BASE_URL + cropGroupUrl;
        URLConstants.POP_FOR_GROUP = URLConstants.BASE_URL + popForGroupUrl;
        URLConstants.USER_POP_FOR_GROUP = URLConstants.BASE_URL + popForGroupUrl;
        URLConstants.SOCIAL_MESSAGES = URLConstants.BASE_URL + socialMessagesUrl;
        URLConstants.SEED_RATE_CROPS = URLConstants.BASE_URL + seedRateCropsUrl;
        URLConstants.UPLOAD_PROFILE_PIC = URLConstants.BASE_URL + uploadPicUrl;
        URLConstants.REFERENCE_DATA = URLConstants.BASE_URL + referenceDataUrl;

        Gson gson = new GsonBuilder().create();
        List<String> genders = gson.fromJson(genderListString, new TypeToken<List<String>>() {
        }.getType());

        Log.d(TAG, genders.toString());
        Log.d(TAG, "chat = " + chatUrl);
        Log.d(TAG, "save = " + getFarmerProfileUrl);
        Log.d(TAG, "reference = " + referenceDataUrl);
        Log.d(TAG, "baseUrl = " + URLConstants.BASE_URL);

        if (onCompleteListener != null) {
            onCompleteListener.onTaskComplete(true);
        }
    }
}

