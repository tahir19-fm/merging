package com.taomish.app.android.farmsanta.farmer.models.api.soil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SoilHealth {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("createdTimestamp")
    @Expose
    private Object createdTimestamp;
    @SerializedName("updatedTimestamp")
    @Expose
    private String updatedTimestamp;
    @SerializedName("startDate")
    @Expose
    private Object startDate;
    @SerializedName("endDate")
    @Expose
    private Object endDate;
    @SerializedName("tenantId")
    @Expose
    private Object tenantId;
    @SerializedName("farmer")
    @Expose
    private Object farmer;
    @SerializedName("farmerMobileNumber")
    @Expose
    private Object farmerMobileNumber;
    @SerializedName("dateOfSample")
    @Expose
    private String dateOfSample;
    @SerializedName("dateOfTesting")
    @Expose
    private String dateOfTesting;
    @SerializedName("sampleDetails")
    @Expose
    private String sampleDetails;
    @SerializedName("labName")
    @Expose
    private String labName;
    @SerializedName("contactPerson")
    @Expose
    private String contactPerson;
    @SerializedName("contactNumber")
    @Expose
    private String contactNumber;
    @SerializedName("territory")
    @Expose
    private String territory;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("parameters")
    @Expose
    private List<Parameter> parameters = null;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Object createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(String updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public Object getStartDate() {
        return startDate;
    }

    public void setStartDate(Object startDate) {
        this.startDate = startDate;
    }

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }

    public Object getTenantId() {
        return tenantId;
    }

    public void setTenantId(Object tenantId) {
        this.tenantId = tenantId;
    }

    public Object getFarmer() {
        return farmer;
    }

    public void setFarmer(Object farmer) {
        this.farmer = farmer;
    }

    public Object getFarmerMobileNumber() {
        return farmerMobileNumber;
    }

    public void setFarmerMobileNumber(Object farmerMobileNumber) {
        this.farmerMobileNumber = farmerMobileNumber;
    }

    public String getDateOfSample() {
        return dateOfSample;
    }

    public void setDateOfSample(String dateOfSample) {
        this.dateOfSample = dateOfSample;
    }

    public String getDateOfTesting() {
        return dateOfTesting;
    }

    public void setDateOfTesting(String dateOfTesting) {
        this.dateOfTesting = dateOfTesting;
    }

    public String getSampleDetails() {
        return sampleDetails;
    }

    public void setSampleDetails(String sampleDetails) {
        this.sampleDetails = sampleDetails;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

}