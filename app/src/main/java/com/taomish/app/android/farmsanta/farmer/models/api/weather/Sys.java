package com.taomish.app.android.farmsanta.farmer.models.api.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys {
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sunrise")
    @Expose
    private Integer sunrise;
    @SerializedName("sunset")
    @Expose
    private Integer sunset;
    @SerializedName("type")
    @Expose
    private Integer type;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
