package com.taomish.app.android.farmsanta.farmer.models.api.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserCrop {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("stage")
    @Expose
    private List<String> stage = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getStage() {
        return stage;
    }

    public void setStage(List<String> stage) {
        this.stage = stage;
    }

}