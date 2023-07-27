package com.taomish.app.android.farmsanta.farmer.datamodel;

import java.util.ArrayList;

public class Product_Data {

    private String title;

    private String message,address,services;
    public int image;

    private ArrayList<Product_Data> singleItemModelArrayList;
    public Product_Data(String title,  int image, String address, String services) {
        this.title = title;
        this.image=image;
        this.address=address;
        this.services=services;
    }

    public Product_Data(String title,  ArrayList<Product_Data> singleItemModelArrayList) {
        this.title = title;
        this.singleItemModelArrayList = singleItemModelArrayList;
    }


    public Product_Data() {

    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getServices() {
        return services;
    }
    public void setServices(String services) {
        this.services = services;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public ArrayList<Product_Data> getSingleItemArrayList() {
        return singleItemModelArrayList;
    }

    public void setSingleItemArrayList(ArrayList<Product_Data> singleItemModelArrayList) {
        this.singleItemModelArrayList = singleItemModelArrayList;
    }
}
