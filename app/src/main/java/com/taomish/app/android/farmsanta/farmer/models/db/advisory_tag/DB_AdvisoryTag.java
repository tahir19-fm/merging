package com.taomish.app.android.farmsanta.farmer.models.db.advisory_tag;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tbl_advisory_tag")
public class DB_AdvisoryTag {

    @SerializedName("uuid")
    @Expose
    @PrimaryKey
    @NonNull
    private String uuid = "";
    @SerializedName("tenantId")
    @Expose
    @ColumnInfo(name = "tenant_id")
    private String tenantId;
    @SerializedName("sequenceNumber")
    @Expose
    @ColumnInfo(name = "sequence_number")
    private Integer sequenceNumber;
    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private String name;
    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description")
    private String description;
    @SerializedName("groupName")
    @Expose
    @ColumnInfo(name = "group_name")
    private String groupName;
    @SerializedName("status")
    @Expose
    @ColumnInfo(name = "status")
    private String status;
    @SerializedName("globalIndicatorUuid")
    @Expose
    @ColumnInfo(name = "global_indicator_uuid")
    private String globalIndicatorUuid;

    @NonNull
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGlobalIndicatorUuid() {
        return globalIndicatorUuid;
    }

    public void setGlobalIndicatorUuid(String globalIndicatorUuid) {
        this.globalIndicatorUuid = globalIndicatorUuid;
    }

}