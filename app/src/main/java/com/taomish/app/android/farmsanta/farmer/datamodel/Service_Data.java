package com.taomish.app.android.farmsanta.farmer.datamodel;

import java.util.ArrayList;

public class Service_Data {

    private String title;

    private String message;
    public int image;

    private ArrayList<Service_Data> singleItemModelArrayList;

    public Service_Data(String title,  int image) {
        this.title = title;
        this.image=image;
    }

    public Service_Data(String title,  ArrayList<Service_Data> singleItemModelArrayList) {
        this.title = title;
        this.singleItemModelArrayList = singleItemModelArrayList;
    }


    public Service_Data() {

    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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

    public ArrayList<Service_Data> getSingleItemArrayList() {
        return singleItemModelArrayList;
    }

    public void setSingleItemArrayList(ArrayList<Service_Data> singleItemModelArrayList) {
        this.singleItemModelArrayList = singleItemModelArrayList;
    }
}
