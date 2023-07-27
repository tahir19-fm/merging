package com.taomish.app.android.farmsanta.farmer.models.api.master;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.taomish.app.android.farmsanta.farmer.models.db.crop.Photo;

import java.util.List;

public class CropMaster {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("createdTimestamp")
    @Expose
    private String createdTimestamp;
    @SerializedName("updatedTimestamp")
    @Expose
    private String updatedTimestamp = null;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("tenantId")
    @Expose
    private String tenantId;
    @SerializedName("cropName")
    @Expose
    private String cropName;
    @SerializedName("territories")
    @Expose
    private List<String> territories = null;
    @SerializedName("regions")
    @Expose
    private List<String> regions = null;
    @SerializedName("cropGroup")
    @Expose
    private String cropGroup;
    @SerializedName("cropDivision")
    @Expose
    private String cropDivision;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;
    @SerializedName("noOfCultivars")
    @Expose
    private Integer noOfCultivars;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("translation")
    @Expose
    private String translation;
    @SerializedName("fertilizerType")
    @Expose
    private int fertilizerType;

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

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public List<String> getTerritories() {
        return territories;
    }

    public void setTerritories(List<String> territories) {
        this.territories = territories;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public String getCropGroup() {
        return cropGroup;
    }

    public void setCropGroup(String cropGroup) {
        this.cropGroup = cropGroup;
    }

    public String getCropDivision() {
        return cropDivision;
    }

    public void setCropDivision(String cropDivision) {
        this.cropDivision = cropDivision;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Integer getNoOfCultivars() {
        return noOfCultivars;
    }

    public void setNoOfCultivars(Integer noOfCultivars) {
        this.noOfCultivars = noOfCultivars;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFertilizerType() {
        return fertilizerType;
    }

    public void setFertilizerType(int fertilizerType) {
        this.fertilizerType = fertilizerType;
    }
}