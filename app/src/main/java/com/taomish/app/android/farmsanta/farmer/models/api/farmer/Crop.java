package com.taomish.app.android.farmsanta.farmer.models.api.farmer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Crop {

    @SerializedName("cropId")
    @Expose
    private String cropId;
    @SerializedName("cultivar")
    @Expose
    private String cultivar;
    @SerializedName("cropType")
    @Expose
    private String cropType;
    @SerializedName("cropName")
    @Expose
    private String cropName;
    @SerializedName("current")
    @Expose
    private Boolean current;
    @SerializedName("stage")
    @Expose
    private String stage;
    @SerializedName("harvestDate")
    @Expose
    private String harvestDate;
    @SerializedName("sowingDate")
    @Expose
    private String sowingDate;
    @SerializedName("expectedYield")
    @Expose
    private Double expectedYield;
    @SerializedName("previousYield")
    @Expose
    private Double previousYield;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("cultivationType")
    @Expose
    private String cultivationType;
    @SerializedName("variety")
    @Expose
    private String variety;
    @SerializedName("area")
    @Expose
    private Area area;
    @SerializedName("rowSpacing")
    @Expose
    private Double rowSpacing;
    @SerializedName("plantSpacing")
    @Expose
    private Double plantSpacing;
    @SerializedName("fertilizerType")
    private int fertilizerType;

    private boolean isAdded;

    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public String getCultivar() {
        return cultivar;
    }

    public void setCultivar(String cultivar) {
        this.cultivar = cultivar;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(String harvestDate) {
        this.harvestDate = harvestDate;
    }

    public String getSowingDate() {
        return sowingDate;
    }

    public void setSowingDate(String sowingDate) {
        this.sowingDate = sowingDate;
    }

    public Double getExpectedYield() {
        return expectedYield;
    }

    public void setExpectedYield(Double expectedYield) {
        this.expectedYield = expectedYield;
    }

    public Double getPreviousYield() {
        return previousYield;
    }

    public void setPreviousYield(Double previousYield) {
        this.previousYield = previousYield;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCultivationType() {
        return cultivationType;
    }

    public void setCultivationType(String cultivationType) {
        this.cultivationType = cultivationType;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Double getRowSpacing() {
        return rowSpacing;
    }

    public void setRowSpacing(Double rowSpacing) {
        this.rowSpacing = rowSpacing;
    }

    public Double getPlantSpacing() {
        return plantSpacing;
    }

    public void setPlantSpacing(Double plantSpacing) {
        this.plantSpacing = plantSpacing;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public int getFertilizerType() {
        return fertilizerType;
    }

    public void setFertilizerType(int fertilizerType) {
        this.fertilizerType = fertilizerType;
    }
}