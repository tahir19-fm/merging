package com.taomish.app.android.farmsanta.farmer.models.db.soil_health;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tbl_crop_recommendation")
public class DB_CropRecommendation {
    @SerializedName("uuid")
    @Expose
    @PrimaryKey
    @NonNull
    private String uuid = "";
    @SerializedName("agronomistRecommendation")
    @Expose
    @ColumnInfo(name = "agronomist_recommendation")
    private String agronomistRecommendation;
    @SerializedName("createdBy")
    @Expose
    @ColumnInfo(name = "created_by")
    private String createdBy;
    @SerializedName("createdTimestamp")
    @Expose
    @ColumnInfo(name = "created_timestamp")
    private String createdTimestamp;
    @SerializedName("crop")
    @Expose
    @ColumnInfo(name = "crop")
    private String crop;
    @SerializedName("impact")
    @Expose
    @ColumnInfo(name = "impact")
    private String impact;
    @SerializedName("nutritionalRecommendation")
    @Expose
    @ColumnInfo(name = "nutritional_recommendation")
    private String nutritionalRecommendation;
    @SerializedName("soilCard")
    @Expose
    @ColumnInfo(name = "soil_card")
    private String soilCard;
    @SerializedName("status")
    @Expose
    @ColumnInfo(name = "status")
    private String status;
    @SerializedName("tenantId")
    @Expose
    @ColumnInfo(name = "tenantId")
    private String tenantId;
    @SerializedName("updatedBy")
    @Expose
    @ColumnInfo(name = "updated_by")
    private String updatedBy;
    @SerializedName("updatedTimestamp")
    @Expose
    @ColumnInfo(name = "updated_time_stamp")
    private String updatedTimestamp;

    @NonNull
    public String getUuid() {
        return uuid;
    }

    public void setUuid(@NonNull String uuid) {
        this.uuid = uuid;
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
}
