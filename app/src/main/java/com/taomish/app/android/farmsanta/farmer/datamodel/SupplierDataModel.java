package com.taomish.app.android.farmsanta.farmer.datamodel;

public class SupplierDataModel {
    private String supplierid;
    private String supplierImage;
    private String supplierName;
    private String supplierLocation;
    private String supplierDetails;
    private String Category;
    private boolean IsFavorite;

    public SupplierDataModel(String Category,String supplierid, String supplierImage, String supplierName, String supplierLocation, String supplierDetails, boolean IsFavorite){
        this.supplierid = supplierid;
        this.supplierImage = supplierImage;
        this.supplierName = supplierName;
        this.supplierLocation = supplierLocation;
        this.IsFavorite = IsFavorite;
        this.supplierDetails=supplierDetails;
        this.Category=Category;
    }
    public String getSupplierid() {
        return supplierid;
    }
    public String getSupplierImage() {
        return supplierImage;
    }
    public String getSupplierName() {
        return supplierName;
    }
    public String getCategory() {
        return Category;
    }
    public String getSupplierLocation() {
        return supplierLocation;
    }
    public String getSupplierDetails() {
        return supplierDetails;
    }
    public boolean getIsFavorite() {
        return IsFavorite;
    }
    public void setFavorite(boolean favorite) {
        IsFavorite = favorite;
    }
}
