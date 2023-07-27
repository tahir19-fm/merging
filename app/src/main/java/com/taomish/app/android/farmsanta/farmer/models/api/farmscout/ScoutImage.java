package com.taomish.app.android.farmsanta.farmer.models.api.farmscout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScoutImage {

    @SerializedName("image")
    private String image;
    @SerializedName("comment")
    private String comment;
    @SerializedName("category")
    private String category;
    @SerializedName("label")
    private String label;
    @SerializedName("plantPart")
    private String plantPart;
    @SerializedName("status")
    private String status;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPlantPart() {
        return plantPart;
    }

    public void setPlantPart(String plantPart) {
        this.plantPart = plantPart;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
