package com.taomish.app.android.farmsanta.farmer.models.db.cultivar;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "tbl_cultivar")
public class DB_Cultivar {
    @SerializedName("uuid")
    @Expose @PrimaryKey @NonNull
    public String uuid = "";
    @SerializedName("additionalDescription")
    @Expose
    @ColumnInfo(name = "additional_description")
    public String additionalDescription;
    @SerializedName("additionalProperties")
    @Expose
    @ColumnInfo(name = "additional_properties")
    public List<String> additionalProperties = null;
    @SerializedName("createdBy")
    @Expose
    @ColumnInfo(name = "created_by")
    public String createdBy;
    @SerializedName("createdTimestamp")
    @Expose
    @ColumnInfo(name = "created_time_stamp")
    public String createdTimestamp;
    @SerializedName("crop")
    @Expose
    @ColumnInfo(name = "crop")
    public String crop;
    @SerializedName("cropDuration")
    @Expose
    @ColumnInfo(name = "crop_duration")
    public CropDuration cropDuration;
    @SerializedName("cultivarGroup")
    @Expose
    @ColumnInfo(name = "cultivar_group")
    public String cultivarGroup;
    @SerializedName("cultivarName")
    @Expose
    @ColumnInfo(name = "cultivar_name")
    public String cultivarName;
    @SerializedName("endDate")
    @Expose
    @ColumnInfo(name = "end_date")
    public String endDate;
    @SerializedName("farmingTypes")
    @Expose
    @ColumnInfo(name = "farming_types")
    public List<String> farmingTypes = null;
    @SerializedName("harvestMonth")
    @Expose
    @ColumnInfo(name = "harvest_month")
    public HarvestMonth harvestMonth;
    @SerializedName("photos")
    @Expose
    @ColumnInfo(name = "photos")
    public List<Photo> photos = null;
    @SerializedName("regions")
    @Expose
    @ColumnInfo(name = "regions")
    public List<String> regions = null;
    @SerializedName("seasons")
    @Expose
    @ColumnInfo(name = "seasons")
    public List<String> seasons = null;
    @SerializedName("sowingMonths")
    @Expose
    @ColumnInfo(name = "sowing_months")
    public List<SowingMonth> sowingMonths = null;
    @SerializedName("startDate")
    @Expose
    @ColumnInfo(name = "start_date")
    public String startDate;
    @SerializedName("status")
    @Expose
    @ColumnInfo(name = "status")
    public String status;
    @SerializedName("tenantId")
    @Expose
    @ColumnInfo(name = "tenant_id")
    public String tenantId;
    @SerializedName("territories")
    @Expose
    @ColumnInfo(name = "territories")
    public List<String> territories = null;
    @SerializedName("updatedBy")
    @Expose
    @ColumnInfo(name = "updated_by")
    public String updatedBy;
    @SerializedName("updatedTimestamp")
    @Expose
    @ColumnInfo(name = "updated_time_stamp")
    public String updatedTimestamp;
    @SerializedName("yieldPotential")
    @Expose
    @ColumnInfo(name = "yield_potential")
    public YieldPotential yieldPotential;
}
