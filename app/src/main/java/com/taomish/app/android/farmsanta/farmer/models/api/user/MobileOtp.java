package com.taomish.app.android.farmsanta.farmer.models.api.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileOtp {
    @SerializedName("response")
    @Expose
    private boolean response;
    @SerializedName("otp")
    @Expose
    private String otp;

    private int responseCode;

    public boolean getResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
