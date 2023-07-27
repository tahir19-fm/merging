package com.taomish.app.android.farmsanta.farmer.models.db.global_indicator;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tbl_global_indicator")
public class DB_GlobalIndicator {
    @SerializedName("uuid") @Expose @PrimaryKey @NonNull
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
    @SerializedName("sequenceNumber")
    @Expose
    @ColumnInfo(name = "sequence_number")
    public Integer sequenceNumber;
    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    public String name;
    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description")
    public String description;
    @SerializedName("groupName")
    @Expose
    @ColumnInfo(name = "group_name")
    public String groupName;
    @SerializedName("status")
    @Expose
    @ColumnInfo(name = "status")
    public String status;
    @SerializedName("globalIndicatorUuid")
    @Expose
    @ColumnInfo(name = "global_indicator_uuid")
    public String globalIndicatorUuid;
}
