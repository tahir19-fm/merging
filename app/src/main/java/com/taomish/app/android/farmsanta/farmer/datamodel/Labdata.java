package com.taomish.app.android.farmsanta.farmer.datamodel;

public class Labdata {
    private String labid;
    private String LabImage;
    private String LabName;
    private String LabPrice;
    private String Testdetails;

    private String State;
    private String District;
    private String Sellerid;
    private boolean IsFavorite;

    public Labdata(){

    }
    public Labdata(String Sellerid,String State,String District,String labid, String LabImage, String LabName, String LabPrice, String Testdetails, boolean IsFavorite){
        this.LabImage = LabImage;
        this.LabName = LabName;
        this.LabPrice = LabPrice;
        this.Testdetails = Testdetails;
        this.IsFavorite = IsFavorite;
        this.State=State;
        this.District=District;
        this.labid=labid;
    }
    public String getSellerid() {
        return Sellerid;
    }
    public String getState() {
        return State;
    }
    public String getDistrict() {
        return District;
    }
    public String getLabImage() {
        return LabImage;
    }
    public String getLabid() {
        return labid;
    }
    public String getLabName() {
        return LabName;
    }
    public String getLabPrice() {
        return LabPrice;
    }
    public String getTestdetails() {
        return Testdetails;
    }
    public boolean getIsFavorite() {
        return IsFavorite;
    }
    public void setFavorite(boolean favorite) {
        IsFavorite = favorite;
    }

}
