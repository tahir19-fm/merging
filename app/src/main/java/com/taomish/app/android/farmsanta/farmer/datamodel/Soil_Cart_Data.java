package com.taomish.app.android.farmsanta.farmer.datamodel;

public class Soil_Cart_Data {
    String productid;
    private String productname;
    private int unit;
    private String price;
    String visitdate,reportdate,contentname;
    private int productimg;
    public Soil_Cart_Data(){

    }
    public Soil_Cart_Data(String productid,String productname,String price,int unit, int productimg,String visitdate,String reportdate,String contentname){
        this.productid=productid;
        this.productname = productname;
        this.price = price;
        this.unit = unit;
        this.productimg = productimg;
        this.visitdate = visitdate;
        this.reportdate = reportdate;
        this.contentname = contentname;
    }

    public int getProductimg() {
        return productimg;
    }

    public String getProductname() {
        return productname;
    }

    public String getPrice() {
        return price;
    }

    public String getProductid() {
        return productid;
    }

    public int getUnit() {
        return unit;
    }

    public String getContentname() {
        return contentname;
    }

    public String getReportdate() {
        return reportdate;
    }

    public String getVisitdate() {
        return visitdate;
    }
}
