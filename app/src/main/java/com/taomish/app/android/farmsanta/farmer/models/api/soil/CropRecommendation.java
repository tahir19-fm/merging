package com.taomish.app.android.farmsanta.farmer.models.api.soil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CropRecommendation {
    @SerializedName("agronomistRecommendation")
    @Expose
    private String agronomistRecommendation;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdTimestamp")
    @Expose
    private String createdTimestamp;
    @SerializedName("crop")
    @Expose
    private String crop;
    @SerializedName("impact")
    @Expose
    private String impact;
    @SerializedName("nutritionalRecommendation")
    @Expose
    private String nutritionalRecommendation;
    @SerializedName("soilCard")
    @Expose
    private String soilCard;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tenantId")
    @Expose
    private String tenantId;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("updatedTimestamp")
    @Expose
    private String updatedTimestamp;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    private String cropName;

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getAgronomistRecommendation() {
        return agronomistRecommendation;
    }

    public void setAgronomistRecommendation(String agronomistRecommendation) {
        this.agronomistRecommendation = agronomistRecommendation;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getNutritionalRecommendation() {
        return nutritionalRecommendation;
    }

    public void setNutritionalRecommendation(String nutritionalRecommendation) {
        this.nutritionalRecommendation = nutritionalRecommendation;
    }

    public String getSoilCard() {
        return soilCard;
    }

    public void setSoilCard(String soilCard) {
        this.soilCard = soilCard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(String updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
