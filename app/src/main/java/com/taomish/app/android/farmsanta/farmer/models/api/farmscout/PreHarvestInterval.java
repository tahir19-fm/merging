package com.taomish.app.android.farmsanta.farmer.models.api.farmscout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreHarvestInterval {

    @SerializedName("unit")
    @Expose
    private Integer unit;
    @SerializedName("uom")
    @Expose
    private String uom;

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

}