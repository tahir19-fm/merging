package com.taomish.app.android.farmsanta.farmer.models.api.calculator;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Calculate {
    @SerializedName("crop")
    @Expose
    private String crop;
    @SerializedName("properties")
    @Expose
    private Properties properties;
    @SerializedName("recordType")
    @Expose
    private String recordType;
    @SerializedName("requiredCalculations")
    @Expose
    private List<String> requiredCalculations = null;
    @SerializedName("seedVariety")
    @Expose
    private String seedVariety;

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public List<String> getRequiredCalculations() {
        return requiredCalculations;
    }

    public void setRequiredCalculations(List<String> requiredCalculations) {
        this.requiredCalculations = requiredCalculations;
    }

    public String getSeedVariety() {
        return seedVariety;
    }

    public void setSeedVariety(String seedVariety) {
        this.seedVariety = seedVariety;
    }

}
