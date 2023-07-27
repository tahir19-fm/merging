package com.taomish.app.android.farmsanta.farmer.models.api.profile;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class CropStage {

    @PrimaryKey
    @SerializedName("uuid")
    @Expose
    @NonNull
    private String uuid = "";
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
    private String updatedTimestamp;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("tenantId")
    @Expose
    private String tenantId;
    @SerializedName("crop")
    @Expose
    private String crop;
    @SerializedName("cultivarGroup")
    @Expose
    private String cultivarGroup;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("cultivars")
    @Expose
    private List<String> cultivars = null;
    @SerializedName("territories")
    @Expose
    private List<String> territories = null;
    @SerializedName("regions")
    @Expose
    private List<String> regions = null;

    @Ignore
    @SerializedName("stages")
    @Expose
    private List<Stage> stages = null;

    @SerializedName("description")
    @Expose
    private String description = null;

    @SerializedName("groupName")
    @Expose
    private String groupName = null;

    @SerializedName("name")
    @Expose
    private String name = null;

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

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getCultivarGroup() {
        return cultivarGroup;
    }

    public void setCultivarGroup(String cultivarGroup) {
        this.cultivarGroup = cultivarGroup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getCultivars() {
        return cultivars;
    }

    public void setCultivars(List<String> cultivars) {
        this.cultivars = cultivars;
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

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}