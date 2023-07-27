package com.taomish.app.android.farmsanta.farmer.models.api.home;

public class MarketPrice {
    private String cropName;
    private String cropPrice;
    private boolean isUp;
    private String cropImageUrl;

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropPrice() {
        return cropPrice;
    }

    public void setCropPrice(String cropPrice) {
        this.cropPrice = cropPrice;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public String getCropImageUrl() {
        return cropImageUrl;
    }

    public void setCropImageUrl(String cropImageUrl) {
        this.cropImageUrl = cropImageUrl;
    }
}
