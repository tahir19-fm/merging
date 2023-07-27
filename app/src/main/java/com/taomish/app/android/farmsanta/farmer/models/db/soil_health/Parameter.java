package com.taomish.app.android.farmsanta.farmer.models.db.soil_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parameter {
    @SerializedName("parameter")
    @Expose
    private String parameter;
    @SerializedName("value")
    @Expose
    private Double value;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("normalValue")
    @Expose
    private NormalValue normalValue;

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public NormalValue getNormalValue() {
        return normalValue;
    }

    public void setNormalValue(NormalValue normalValue) {
        this.normalValue = normalValue;
    }

}

