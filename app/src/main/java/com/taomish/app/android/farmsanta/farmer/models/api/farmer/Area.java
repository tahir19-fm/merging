package com.taomish.app.android.farmsanta.farmer.models.api.farmer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Area {
    @SerializedName("unit")
    @Expose
    private Double unit;
    @SerializedName("uom")
    @Expose
    private String uom;

    public Double getUnit() {
        return unit;
    }

    public void setUnit(Double unit) {
        this.unit = unit;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
}
