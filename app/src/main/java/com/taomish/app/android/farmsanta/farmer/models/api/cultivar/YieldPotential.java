package com.taomish.app.android.farmsanta.farmer.models.api.cultivar;

import com.google.gson.annotations.SerializedName;

public class YieldPotential {

    @SerializedName("max")
    private Float max;
    @SerializedName("min")
    private Float min;

    public Float getMax() {
        return max;
    }

    public void setMax(Float max) {
        this.max = max;
    }

    public Float getMin() {
        return min;
    }

    public void setMin(Float min) {
        this.min = min;
    }

}
