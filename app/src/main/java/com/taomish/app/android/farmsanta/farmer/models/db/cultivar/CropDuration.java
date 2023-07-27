package com.taomish.app.android.farmsanta.farmer.models.db.cultivar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CropDuration {

    @SerializedName("max")
    @Expose
    private Integer max;
    @SerializedName("min")
    @Expose
    private Integer min;

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

}
