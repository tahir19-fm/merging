package com.taomish.app.android.farmsanta.farmer.models.db.soil_health;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "tbl_soil_health")
public class DB_SoilHealth {
    @SerializedName("uuid") @Expose @PrimaryKey @NonNull
    public String uuid = "";
    @SerializedName("createdBy")
    @Expose
    @ColumnInfo(name = "created_by")
    public String createdBy;
    @SerializedName("createdTimestamp")
    @Expose
    @ColumnInfo(name = "created_time_stamp")
    public String createdTimestamp;
    @SerializedName("endDate")
    @Expose
    @ColumnInfo(name = "end_date")
    public String endDate;
    @SerializedName("contactNumber")
    @Expose
    @ColumnInfo(name = "contact_number")
    public String contactNumber;
    @SerializedName("contactPerson")
    @Expose
    @ColumnInfo(name = "contact_person")
    public String contactPerson;
    @SerializedName("farmer")
    @Expose
    @ColumnInfo(name = "farmer")
    public String farmer;
    @SerializedName("farmerMobileNumber")
    @Expose
    @ColumnInfo(name = "farmer_mobile_number")
    public String farmerMobileNumber;
    @SerializedName("startDate")
    @Expose
    @ColumnInfo(name = "start_date")
    public String startDate;
    @SerializedName("tenantId")
    @Expose
    @ColumnInfo(name = "tenant_id")
    public String tenantId;
    @SerializedName("updatedTimestamp")
    @Expose
    @ColumnInfo(name = "updated_time_stamp")
    public String updatedTimestamp;
    @SerializedName("updatedBy")
    @Expose
    @ColumnInfo(name = "updated_by")
    public String updatedBy;
    @SerializedName("dateOfSample")
    @Expose
    @ColumnInfo(name = "date_of_sample")
    public String dateOfSample;
    @SerializedName("dateOfTesting")
    @Expose
    @ColumnInfo(name = "date_of_testing")
    public String dateOfTesting;
    @SerializedName("sampleDetails")
    @Expose
    @ColumnInfo(name = "sample_details")
    public String sampleDetails;
    @SerializedName("labName")
    @Expose
    @ColumnInfo(name = "lab_name")
    public String labName;
    @SerializedName("territory")
    @Expose
    @ColumnInfo(name = "territory")
    public String territory;
    @SerializedName("territoryName")
    @Expose
    @ColumnInfo(name = "territory_name")
    public String territoryName;
    @SerializedName("region")
    @Expose
    @ColumnInfo(name = "region")
    public String region;
    @SerializedName("regionName")
    @Expose
    @ColumnInfo(name = "region_name")
    public String regionName;
    @SerializedName("status")
    @Expose
    @ColumnInfo(name = "status")
    public String status;
    @SerializedName("parameters")
    @Expose
    @ColumnInfo(name = "parameters")
    public List<Parameter> parameters = null;
}

