package com.taomish.app.android.farmsanta.farmer.models.db.cultivar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YieldPotential {

    @SerializedName("max")
    @Expose
    private Double max;
    @SerializedName("min")
    @Expose
    private Double min;

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

}
