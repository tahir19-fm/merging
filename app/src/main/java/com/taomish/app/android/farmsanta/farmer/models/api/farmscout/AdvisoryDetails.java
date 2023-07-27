package com.taomish.app.android.farmsanta.farmer.models.api.farmscout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdvisoryDetails {

    @SerializedName("localName")
    @Expose
    private String localName;
    @SerializedName("symptomsOfAttack")
    @Expose
    private String symptomsOfAttack;
    @SerializedName("favourableConditions")
    @Expose
    private String favourableConditions;
    @SerializedName("preventiveMeasures")
    @Expose
    private String preventiveMeasures;
    @SerializedName("culturalMechanicalControl")
    @Expose
    private String culturalMechanicalControl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("culturalControl")
    @Expose
    private Object culturalControl;

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getSymptomsOfAttack() {
        return symptomsOfAttack;
    }

    public void setSymptomsOfAttack(String symptomsOfAttack) {
        this.symptomsOfAttack = symptomsOfAttack;
    }

    public String getFavourableConditions() {
        return favourableConditions;
    }

    public void setFavourableConditions(String favourableConditions) {
        this.favourableConditions = favourableConditions;
    }

    public String getPreventiveMeasures() {
        return preventiveMeasures;
    }

    public void setPreventiveMeasures(String preventiveMeasures) {
        this.preventiveMeasures = preventiveMeasures;
    }

    public String getCulturalMechanicalControl() {
        return culturalMechanicalControl;
    }

    public void setCulturalMechanicalControl(String culturalMechanicalControl) {
        this.culturalMechanicalControl = culturalMechanicalControl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getCulturalControl() {
        return culturalControl;
    }

    public void setCulturalControl(Object culturalControl) {
        this.culturalControl = culturalControl;
    }

}