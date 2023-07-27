package com.taomish.app.android.farmsanta.farmer.models.api.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CropAdvisory {

    @SerializedName("advisory")
    @Expose
    private String advisory;
    @SerializedName("advisoryTag")
    @Expose
    private String advisoryTag;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdTimestamp")
    @Expose
    private String createdTimestamp;
    @SerializedName("crop")
    @Expose
    private String crop;
    @SerializedName("cropName")
    @Expose
    private String cropName;
    @SerializedName("growthStageName")
    @Expose
    private String growthStageName;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("farmerGroups")
    @Expose
    private List<String> farmerGroups = null;
    @SerializedName("growthStage")
    @Expose
    private String growthStage;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;
    @SerializedName("productRecommendation")
    @Expose
    private String productRecommendation;
    @SerializedName("regions")
    @Expose
    private List<String> regions = null;
    @SerializedName("startDate")
    @Expose
    private String startDate;
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
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String  lastName;
    @SerializedName("advisoryTagName")
    @Expose
    private String  advisoryTagName;
    @SerializedName("profileImage")
    @Expose
    private String  profileImage;
    @SerializedName("agronomyManager")
    @Expose
    private Boolean  agronomyManager;
    @SerializedName("superAdmin")
    @Expose
    private Boolean  superAdmin;

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getAdvisory() {
        return advisory;
    }

    public void setAdvisory(String advisory) {
        this.advisory = advisory;
    }

    public String getAdvisoryTag() {
        return advisoryTag;
    }

    public void setAdvisoryTag(String advisoryTag) {
        this.advisoryTag = advisoryTag;
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


    public String getGrowthStageName() {
        return growthStageName;
    }

    public void setGrowthStageName(String growthStageName) {
        this.crop = growthStageName;
    }
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<String> getFarmerGroups() {
        return farmerGroups;
    }

    public void setFarmerGroups(List<String> farmerGroups) {
        this.farmerGroups = farmerGroups;
    }

    public String getGrowthStage() {
        return growthStage;
    }

    public void setGrowthStage(String growthStage) {
        this.growthStage = growthStage;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getProductRecommendation() {
        return productRecommendation;
    }

    public void setProductRecommendation(String productRecommendation) {
        this.productRecommendation = productRecommendation;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdvisoryTagName() {
        return advisoryTagName;
    }

    public void setAdvisoryTagName(String advisoryTagName) {
        this.advisoryTagName = advisoryTagName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Boolean getAgronomyManager() {
        return agronomyManager;
    }

    public void setAgronomyManager(Boolean agronomyManager) {
        this.agronomyManager = agronomyManager;
    }

    public Boolean getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Boolean superAdmin) {
        this.superAdmin = superAdmin;
    }
}
