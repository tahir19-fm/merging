package com.taomish.app.android.farmsanta.farmer.models.api.calculator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.taomish.app.android.farmsanta.farmer.constants.CalculatorConstants;

public class Properties {

    @SerializedName(CalculatorConstants.SeedRate.SPACING)
    @Expose
    private String spacing;
    @SerializedName(CalculatorConstants.SeedRate.RESULT_KG)
    @Expose
    private String result_kg;
    @SerializedName(CalculatorConstants.SeedRate.CULTIVABLE_AREA)
    @Expose
    private String cultivable_area;
    @SerializedName(CalculatorConstants.SeedRate.GAP)
    @Expose
    private String gap;
    @SerializedName(CalculatorConstants.SeedRate.GERMINATION)
    @Expose
    private String germination;
    @SerializedName(CalculatorConstants.SeedRate.PER_HILL)
    @Expose
    private String perHill;
    @SerializedName(CalculatorConstants.SeedRate.PURITY)
    @Expose
    private String purity;
    @SerializedName(CalculatorConstants.SeedRate.RESULT_LBS)
    @Expose
    private String result_lbs;
    @SerializedName(CalculatorConstants.SeedRate.TEST_WEIGHT)
    @Expose
    private String test_weight;
    @SerializedName(CalculatorConstants.SeedRate.TOTAL_POP)
    @Expose
    private String total_pop;

    public String getSpacing() {
        return spacing;
    }

    public void setSpacing(String spacing) {
        this.spacing = spacing;
    }

    public String getResult_kg() {
        return result_kg;
    }

    public void setResult_kg(String result_kg) {
        this.result_kg = result_kg;
    }

    public String getCultivable_area() {
        return cultivable_area;
    }

    public void setCultivable_area(String cultivable_area) {
        this.cultivable_area = cultivable_area;
    }

    public String getGap() {
        return gap;
    }

    public void setGap(String gap) {
        this.gap = gap;
    }

    public String getGermination() {
        return germination;
    }

    public void setGermination(String germination) {
        this.germination = germination;
    }

    public String getPerHill() {
        return perHill;
    }

    public void setPerHill(String perHill) {
        this.perHill = perHill;
    }

    public String getPurity() {
        return purity;
    }

    public void setPurity(String purity) {
        this.purity = purity;
    }

    public String getResult_lbs() {
        return result_lbs;
    }

    public void setResult_lbs(String result_lbs) {
        this.result_lbs = result_lbs;
    }

    public String getTest_weight() {
        return test_weight;
    }

    public void setTest_weight(String test_weight) {
        this.test_weight = test_weight;
    }

    public String getTotal_pop() {
        return total_pop;
    }

    public void setTotal_pop(String total_pop) {
        this.total_pop = total_pop;
    }
}