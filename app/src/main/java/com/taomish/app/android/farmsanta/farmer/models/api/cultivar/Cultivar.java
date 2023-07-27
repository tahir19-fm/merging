package com.taomish.app.android.farmsanta.farmer.models.api.cultivar;

import com.google.gson.annotations.SerializedName;
import com.taomish.app.android.farmsanta.farmer.models.api.pop.Photo;

import java.util.List;

public class Cultivar {
    @SerializedName("additionalDescription")
    private String additionalDescription;
    @SerializedName("additionalProperties")
    private List<String> additionalProperties = null;
    @SerializedName("createdBy")
    private String createdBy;
    @SerializedName("createdTimestamp")
    private String createdTimestamp;
    @SerializedName("ownerName")
    private String ownerName;
    @SerializedName("crop")
    private String crop;
    @SerializedName("cropDuration")
    private CropDuration cropDuration;
    @SerializedName("cultivarGroups")
    private List<String> cultivarGroups;
    @SerializedName("cultivarName")
    private String cultivarName;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("farmingTypes")
    private List<String> farmingTypes = null;
    @SerializedName("harvestMonth")
    private HarvestMonth harvestMonth;
    @SerializedName("photos")
    private List<Photo> photos = null;
    @SerializedName("regions")
    private List<String> regions = null;
    @SerializedName("seasons")
    private List<String> seasons = null;
    @SerializedName("sowingMonths")
    private List<SowingMonth> sowingMonths = null;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("status")
    private String status;
    @SerializedName("tenantId")
    private String tenantId;
    @SerializedName("territories")
    private List<String> territories = null;
    @SerializedName("updatedBy")
    private String updatedBy;
    @SerializedName("updatedTimestamp")
    private String updatedTimestamp;
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("yieldPotential")
    private YieldPotential yieldPotential;

    public String getAdditionalDescription() {
        return additionalDescription;
    }

    public void setAdditionalDescription(String additionalDescription) {
        this.additionalDescription = additionalDescription;
    }

    public List<String> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(List<String> additionalProperties) {
        this.additionalProperties = additionalProperties;
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

    public CropDuration getCropDuration() {
        return cropDuration;
    }

    public void setCropDuration(CropDuration cropDuration) {
        this.cropDuration = cropDuration;
    }

    public List<String> getCultivarGroups() {
        return cultivarGroups;
    }

    public void setCultivarGroups(List<String> cultivarGroups) {
        this.cultivarGroups = cultivarGroups;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCultivarName() {
        return cultivarName;
    }

    public void setCultivarName(String cultivarName) {
        this.cultivarName = cultivarName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<String> getFarmingTypes() {
        return farmingTypes;
    }

    public void setFarmingTypes(List<String> farmingTypes) {
        this.farmingTypes = farmingTypes;
    }

    public HarvestMonth getHarvestMonth() {
        return harvestMonth;
    }

    public void setHarvestMonth(HarvestMonth harvestMonth) {
        this.harvestMonth = harvestMonth;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public List<String> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<String> seasons) {
        this.seasons = seasons;
    }

    public List<SowingMonth> getSowingMonths() {
        return sowingMonths;
    }

    public void setSowingMonths(List<SowingMonth> sowingMonths) {
        this.sowingMonths = sowingMonths;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
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

    public List<String> getTerritories() {
        return territories;
    }

    public void setTerritories(List<String> territories) {
        this.territories = territories;
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

    public YieldPotential getYieldPotential() {
        return yieldPotential;
    }

    public void setYieldPotential(YieldPotential yieldPotential) {
        this.yieldPotential = yieldPotential;
    }
}
