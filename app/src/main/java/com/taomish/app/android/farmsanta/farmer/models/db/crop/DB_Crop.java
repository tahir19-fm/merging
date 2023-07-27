package com.taomish.app.android.farmsanta.farmer.models.db.crop;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "tbl_crop")
public class DB_Crop {
    @SerializedName("uuid")
    @Expose
    @PrimaryKey
    @NonNull
    public String uuid = "";
    @SerializedName("createdBy")
    @Expose
    @ColumnInfo(name = "created_by")
    public String createdBy;
    @SerializedName("updatedBy")
    @Expose
    @ColumnInfo(name = "updated_by")
    public String updatedBy;
    @SerializedName("createdTimestamp")
    @Expose
    @ColumnInfo(name = "created_timestamp")
    public String createdTimestamp;
    @SerializedName("updatedTimestamp")
    @Expose
    @ColumnInfo(name = "updated_timestamp")
    public String updatedTimestamp;
    @SerializedName("startDate")
    @Expose
    @ColumnInfo(name = "start_date")
    public String startDate;
    @SerializedName("endDate")
    @Expose
    @ColumnInfo(name = "end_date")
    public String endDate;
    @SerializedName("tenantId")
    @Expose
    @ColumnInfo(name = "tenant_id")
    public String tenantId;
    @SerializedName("cropName")
    @Expose
    @ColumnInfo(name = "crop_name")
    public String cropName;
    @SerializedName("territories")
    @Expose
    @ColumnInfo(name = "territories")
    public List<String> territories = null;
    @SerializedName("regions")
    @Expose
    @ColumnInfo(name = "regions")
    public List<String> regions = null;
    @SerializedName("cropGroup")
    @Expose
    @ColumnInfo(name = "crop_group")
    public String cropGroup;
    @SerializedName("cropDivision")
    @Expose
    @ColumnInfo(name = "crop_division")
    public String cropDivision;
    @SerializedName("photos")
    @Expose
    @ColumnInfo(name = "photos")
    public List<Photo> photos = null;
    @SerializedName("noOfCultivars")
    @Expose
    @ColumnInfo(name = "no_of_cultivars")
    public Integer noOfCultivars;
    @SerializedName("status")
    @Expose
    @ColumnInfo(name = "status")
    public String status;
    @SerializedName("fertilizerType")
    @Expose
    @ColumnInfo(name = "fertilizerType")
    public int fertilizerType;
}