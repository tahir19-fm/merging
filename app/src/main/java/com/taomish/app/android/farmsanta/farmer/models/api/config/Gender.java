package com.taomish.app.android.farmsanta.farmer.models.api.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gender {
    @SerializedName("gender")
    @Expose
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
