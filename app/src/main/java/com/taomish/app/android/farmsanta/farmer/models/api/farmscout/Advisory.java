package com.taomish.app.android.farmsanta.farmer.models.api.farmscout;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Advisory {

    @SerializedName("uuid")
    private String uuid;
    @SerializedName("createdBy")
    private String createdBy;
    @SerializedName("updatedBy")
    private String updatedBy;
    @SerializedName("createdTimestamp")
    private String createdTimestamp;
    @SerializedName("updatedTimestamp")
    private String updatedTimestamp;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("tenantId")
    private String tenantId;
    @SerializedName("label")
    private String label;
    @SerializedName("category")
    private String category;
    @SerializedName("farmerGroup")
    private String farmerGroup;
    @SerializedName("cropName")
    private String cropName;
    @SerializedName("images")
    private List<Image> images = null;
    @SerializedName("advisoryDetails")
    private AdvisoryDetails advisoryDetails;
    @SerializedName("advisoryTable")
    private List<AdvisoryTable> advisoryTable = null;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(String updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFarmerGroup() {
        return farmerGroup;
    }

    public void setFarmerGroup(String farmerGroup) {
        this.farmerGroup = farmerGroup;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public AdvisoryDetails getAdvisoryDetails() {
        return advisoryDetails;
    }

    public void setAdvisoryDetails(AdvisoryDetails advisoryDetails) {
        this.advisoryDetails = advisoryDetails;
    }

    public List<AdvisoryTable> getAdvisoryTable() {
        return advisoryTable;
    }

    public void setAdvisoryTable(List<AdvisoryTable> advisoryTable) {
        this.advisoryTable = advisoryTable;
    }

}