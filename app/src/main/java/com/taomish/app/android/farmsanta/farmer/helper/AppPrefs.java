package com.taomish.app.android.farmsanta.farmer.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.taomish.app.android.farmsanta.farmer.constants.AppConstants;

import java.util.Date;

public class
AppPrefs {
    private final SharedPreferences sharedPreferences;

    public AppPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences(
                AppConstants.PreferenceConstants.PREF_NAME,
                Context.MODE_PRIVATE);
    }

    public String getUserToken() {
        return sharedPreferences.getString(
                AppConstants.PreferenceConstants.KEY_USER_TOKEN, "");
    }

    public void setUserToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.PreferenceConstants.KEY_USER_TOKEN, token);
        editor.apply();
    }

    public String getRefreshToken() {
        return sharedPreferences.getString(
                AppConstants.PreferenceConstants.KEY_REFRESH_TOKEN, "");
    }

    public void setRefreshToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.PreferenceConstants.KEY_REFRESH_TOKEN, token);
        editor.apply();
    }

    public String getFirebaseToken() {
        return sharedPreferences.getString(
                AppConstants.PreferenceConstants.KEY_FIREBASE_TOKEN, "");
    }

    public void setFirebaseToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.PreferenceConstants.KEY_FIREBASE_TOKEN, token);
        editor.apply();
    }

    public boolean isUserProfileCompleted() {
        return sharedPreferences.getBoolean(
                AppConstants.PreferenceConstants.KEY_USER_PROFILE_DONE, false);
    }

    public void setUserProfileCompleted(boolean done) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(AppConstants.PreferenceConstants.KEY_USER_PROFILE_DONE, done);
        editor.apply();
    }

    public String getPhoneNumber() {
        return sharedPreferences.getString(
                AppConstants.PreferenceConstants.KEY_PHONE_NUM, "");
    }

    public void setPhoneNumber(String phoneNumber) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.PreferenceConstants.KEY_PHONE_NUM, phoneNumber);
        editor.apply();
    }

    public String getCountryCode() {
        return sharedPreferences.getString(
                AppConstants.PreferenceConstants.KEY_COUNTRY_CODE, "");
    }

    public void setVerificationId(String countryCode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.PreferenceConstants.KEY_VERIFICATION_ID, countryCode);
        editor.apply();
    }public String getVerificationId() {
        return sharedPreferences.getString(
                AppConstants.PreferenceConstants.KEY_VERIFICATION_ID, "");
    }

    public void setResendToken(String countryCode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.PreferenceConstants.KEY_RESEND_TOKEN, countryCode);
        editor.apply();
    }public String getResendToken() {
        return sharedPreferences.getString(
                AppConstants.PreferenceConstants.KEY_RESEND_TOKEN, "");
    }

    public void setCountryCode(String countryCode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.PreferenceConstants.KEY_COUNTRY_CODE, countryCode);
        editor.apply();
    }


    public String getCountryName() {
        return sharedPreferences.getString(
                AppConstants.PreferenceConstants.KEY_COUNTRY_NAME, "");
    }

    public void setCountryName(String countryCode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.PreferenceConstants.KEY_COUNTRY_NAME, countryCode);
        editor.apply();
    }


    public String getLanguageId() {
        return sharedPreferences.getString(
                AppConstants.PreferenceConstants.KEY_LANGUAGE_ID, "1");
    }

    public void setLanguageId(String languageId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.PreferenceConstants.KEY_LANGUAGE_ID, languageId);
        editor.apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(
                AppConstants.PreferenceConstants.KEY_USER_NAME, "");
    }

    public void setUserName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.PreferenceConstants.KEY_USER_NAME, name);
        editor.apply();
    }

    public String getName() {
        return sharedPreferences.getString(
                AppConstants.PreferenceConstants.KEY_NAME, "");
    }

    public String getFirstName() {
        return sharedPreferences.getString(
                AppConstants.PreferenceConstants.KEY_FIRST_NAME, "");
    }

    public String getLastName() {
        return sharedPreferences.getString(
                AppConstants.PreferenceConstants.KEY_LAST_NAME, "");
    }

    public void setName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.PreferenceConstants.KEY_NAME, name);
        editor.apply();
    }

    public void setFirstName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.PreferenceConstants.KEY_FIRST_NAME, name);
        editor.apply();
    }

    public void setLastName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.PreferenceConstants.KEY_LAST_NAME, name);
        editor.apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(
                AppConstants.PreferenceConstants.KEY_EMAIL, "");
    }

    public void setEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.PreferenceConstants.KEY_EMAIL, email);
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(
                AppConstants.PreferenceConstants.KEY_USER_UUID, "");
    }

    public void setUserId(String uuid) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.PreferenceConstants.KEY_USER_UUID, uuid);
        editor.apply();
    }

    public Date getLastSyncTime() {
        return new Date(sharedPreferences.getLong(AppConstants.PreferenceConstants.KEY_LAST_DB_SYNC, 0));
    }

    public void setLastSyncTime(Date date) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(AppConstants.PreferenceConstants.KEY_LAST_DB_SYNC, date.getTime());
        editor.apply();
    }

    public boolean getIsTokenSent() {
        return sharedPreferences.getBoolean(AppConstants.PreferenceConstants.KEY_IS_TOKEN_SENT, false);
    }


    public void setIsTokenSent(boolean isTokenSent) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(AppConstants.PreferenceConstants.KEY_IS_TOKEN_SENT, isTokenSent);
        editor.apply();
    }

    public void clearUserDetails() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(AppConstants.PreferenceConstants.KEY_USER_TOKEN);
        editor.remove(AppConstants.PreferenceConstants.KEY_REFRESH_TOKEN);
        editor.remove(AppConstants.PreferenceConstants.KEY_USER_NAME);
        editor.remove(AppConstants.PreferenceConstants.KEY_NAME);
        editor.remove(AppConstants.PreferenceConstants.KEY_EMAIL);
        editor.remove(AppConstants.PreferenceConstants.KEY_PHONE_NUM);
        editor.remove(AppConstants.PreferenceConstants.KEY_COUNTRY_CODE);
        editor.remove(AppConstants.PreferenceConstants.KEY_USER_PROFILE_DONE);
        editor.remove(AppConstants.PreferenceConstants.KEY_USER_UUID);
        editor.remove(AppConstants.PreferenceConstants.KEY_FIREBASE_TOKEN);
        editor.remove(AppConstants.PreferenceConstants.KEY_LAST_DB_SYNC);
        editor.clear();
        editor.apply();
    }
}
