package com.taomish.app.android.farmsanta.farmer.models.api.farmscout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("scoutingID")
    private String scoutingID;
    @SerializedName("imageId")
    private String imageId;

    public String getScoutingID() {
        return scoutingID;
    }

    public void setScoutingID(String scoutingID) {
        this.scoutingID = scoutingID;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

}