package com.taomish.app.android.farmsanta.farmer.models.db.crop_stage;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tbl_crop_stage")
public class DB_CropStage {

    @SerializedName("uuid")
    @Expose @PrimaryKey
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
    @ColumnInfo(name = "created_time_stamp")
    public String createdTimestamp;
    @SerializedName("updatedTimestamp")
    @Expose
    @ColumnInfo(name = "updated_time_stamp")
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
    @SerializedName("crop")
    @Expose
    @ColumnInfo(name = "crop")
    public String crop;
    @SerializedName("cropName")
    @Expose
    @ColumnInfo(name = "crop_name")
    public String cropName;
    @SerializedName("cultivarGroup")
    @Expose
    @ColumnInfo(name = "cultivar_group")
    public String cultivarGroup;
    @SerializedName("cultivarGroupName")
    @Expose
    @ColumnInfo(name = "cultivar_group_name")
    public String cultivarGroupName;
    @SerializedName("status")
    @Expose
    @ColumnInfo(name = "status")
    public String status;
    @SerializedName("cultivars")
    @Expose
    @ColumnInfo(name = "cultivars")
    public List<String> cultivars = null;
    @SerializedName("cultivarName")
    @Expose
    @ColumnInfo(name = "cultivar_name")
    public String cultivarName;
    @SerializedName("territories")
    @Expose
    @ColumnInfo(name = "territories")
    public List<String> territories = null;
    @SerializedName("territoryName")
    @Expose
    @ColumnInfo(name = "territory_name")
    public String territoryName;
    @SerializedName("regions")
    @Expose
    @ColumnInfo(name = "regions")
    public List<String> regions = null;
    @SerializedName("regionName")
    @Expose
    @ColumnInfo(name = "region_name")
    public String regionName;
    @SerializedName("stages")
    @Expose
    @ColumnInfo(name = "stages")
    public List<Stage> stages = null;

    @SerializedName("description")
    @Expose
    private String description = null;

    @SerializedName("name")
    @Expose
    private String name = null;

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
}
