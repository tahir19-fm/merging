package com.taomish.app.android.farmsanta.farmer.models.api.farmscout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdvisoryTable {

    @SerializedName("dataLabel")
    @Expose
    private String dataLabel;
    @SerializedName("formulation")
    @Expose
    private String formulation;
    @SerializedName("formulationType")
    @Expose
    private String formulationType;
    @SerializedName("prodName")
    @Expose
    private String prodName;
    @SerializedName("dosage")
    @Expose
    private Dosage dosage;
    @SerializedName("dosageMethod")
    @Expose
    private String dosageMethod;
    @SerializedName("waterRequirement")
    @Expose
    private WaterRequirement waterRequirement;
    @SerializedName("preHarvestInterval")
    @Expose
    private PreHarvestInterval preHarvestInterval;
    @SerializedName("applicationMethod")
    @Expose
    private String applicationMethod;
    @SerializedName("modeOfAction")
    @Expose
    private String modeOfAction;
    @SerializedName("toxicityLevel")
    @Expose
    private String toxicityLevel;

    public String getDataLabel() {
        return dataLabel;
    }

    public void setDataLabel(String dataLabel) {
        this.dataLabel = dataLabel;
    }

    public String getFormulation() {
        return formulation;
    }

    public void setFormulation(String formulation) {
        this.formulation = formulation;
    }

    public String getFormulationType() {
        return formulationType;
    }

    public void setFormulationType(String formulationType) {
        this.formulationType = formulationType;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Dosage getDosage() {
        return dosage;
    }

    public void setDosage(Dosage dosage) {
        this.dosage = dosage;
    }

    public String getDosageMethod() {
        return dosageMethod;
    }

    public void setDosageMethod(String dosageMethod) {
        this.dosageMethod = dosageMethod;
    }

    public WaterRequirement getWaterRequirement() {
        return waterRequirement;
    }

    public void setWaterRequirement(WaterRequirement waterRequirement) {
        this.waterRequirement = waterRequirement;
    }

    public PreHarvestInterval getPreHarvestInterval() {
        return preHarvestInterval;
    }

    public void setPreHarvestInterval(PreHarvestInterval preHarvestInterval) {
        this.preHarvestInterval = preHarvestInterval;
    }

    public String getApplicationMethod() {
        return applicationMethod;
    }

    public void setApplicationMethod(String applicationMethod) {
        this.applicationMethod = applicationMethod;
    }

    public String getModeOfAction() {
        return modeOfAction;
    }

    public void setModeOfAction(String modeOfAction) {
        this.modeOfAction = modeOfAction;
    }

    public String getToxicityLevel() {
        return toxicityLevel;
    }

    public void setToxicityLevel(String toxicityLevel) {
        this.toxicityLevel = toxicityLevel;
    }

}