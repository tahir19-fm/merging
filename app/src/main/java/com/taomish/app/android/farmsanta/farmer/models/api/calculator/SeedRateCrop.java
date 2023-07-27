package com.taomish.app.android.farmsanta.farmer.models.api.calculator;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeedRateCrop {

    @SerializedName("crop")
    @Expose
    private String crop;
    @SerializedName("varieties")
    @Expose
    private List<String> varieties = null;

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public List<String> getVarieties() {
        return varieties;
    }

    public void setVarieties(List<String> varieties) {
        this.varieties = varieties;
    }

}