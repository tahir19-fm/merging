package com.taomish.app.android.farmsanta.farmer.models.api.farmer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordinate implements Comparable<Coordinate> {

    @SerializedName("index")
    @Expose
    private Integer index;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int compareTo(Coordinate o) {
        if (index != null && o.index != null) {
            return index.compareTo(o.index);
        }
        return 0;
    }
}