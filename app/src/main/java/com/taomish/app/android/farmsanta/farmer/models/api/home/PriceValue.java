package com.taomish.app.android.farmsanta.farmer.models.api.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceValue {
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("value")
    @Expose
    private Double value;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
