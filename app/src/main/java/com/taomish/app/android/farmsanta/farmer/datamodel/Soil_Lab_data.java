package com.taomish.app.android.farmsanta.farmer.datamodel;

import java.util.ArrayList;

public class Soil_Lab_data {
    int productimg;
    String productid,productname,testservices,reportday,price;
    private ArrayList<Seller_Product_Data> singleItemModelArrayList;

    public Soil_Lab_data(String productid,int productimg,String productname,String price,String testservices,String reportday) {
        this.productimg = productimg;
        this.productid=productid;
        this.productname=productname;
        this.price=price;
        this.testservices = testservices;
        this.reportday=reportday;
    }

    public Soil_Lab_data(String productname,  ArrayList<Seller_Product_Data> singleItemModelArrayList) {
        this.productname = productname;
        this.singleItemModelArrayList = singleItemModelArrayList;
    }

    public String getProductname() {
        return productname;
    }

    public String getProductid() {
        return productid;
    }

    public String getPrice() {
        return price;
    }

    public int getProductimg() {
        return productimg;
    }

    public String getReportday() {
        return reportday;
    }

    public String getTestservices() {
        return testservices;
    }
    public ArrayList<Seller_Product_Data> getSingleItemModelArrayList() {
        return singleItemModelArrayList;
    }
}

