package com.taomish.app.android.farmsanta.farmer.datamodel;

import java.util.ArrayList;

public class Store_Data {

    private String title;

    private String message;
    public int image;
    private String noofuser;
    private String rating;

    private ArrayList<Store_Data> singleItemModelArrayList;

    public Store_Data(String title,  int image,String rating, String noofuser) {
        this.title = title;
        this.image=image;
        this.noofuser=noofuser;
        this.rating=rating;
    }
    public Store_Data(String title, String rating, String noofuser, ArrayList<Store_Data> singleItemModelArrayList) {
        this.title = title;
        this.rating=rating;
        this.noofuser=noofuser;
        this.singleItemModelArrayList = singleItemModelArrayList;
    }

    public Store_Data() {

    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getNoofuser() {
        return noofuser;
    }
    public void setNoofuser(String noofuser) {
        this.noofuser = noofuser;
    }

    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public ArrayList<Store_Data> getSingleItemArrayList() {
        return singleItemModelArrayList;
    }

    public void setSingleItemArrayList(ArrayList<Store_Data> singleItemModelArrayList) {
        this.singleItemModelArrayList = singleItemModelArrayList;
    }
}

