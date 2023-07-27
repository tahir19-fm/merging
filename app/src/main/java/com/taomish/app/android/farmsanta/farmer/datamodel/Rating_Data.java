package com.taomish.app.android.farmsanta.farmer.datamodel;

import java.util.ArrayList;

public class Rating_Data {
    private String name;
    private String id;
    private String message;
    public int image;

    public double ratingnumber;

    public int likenumber;

    public int dislikenumber;
    boolean userlike,userdislike;
    private ArrayList<Rating_Data> singleItemModelArrayList;
    public Rating_Data(String id,String name, int image, String message, int likenumber, int dislikenumber, double ratingnumber,boolean userlike,boolean userdislike) {
        this.id=id;
        this.name = name;
        this.image=image;
        this.message = message;
        this.likenumber=likenumber;
        this.dislikenumber=dislikenumber;
        this.ratingnumber=ratingnumber;
        this.userlike=userlike;
        this.userdislike=userdislike;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Rating_Data> getSingleItemModelArrayList() {
        return singleItemModelArrayList;
    }

    public int getImage() {
        return image;
    }

    public double getRatingnumber() {
        return ratingnumber;
    }

    public int getDislikenumber() {
        return dislikenumber;
    }

    public void setLikenumber(int likenumber) {
        this.likenumber = likenumber;
    }

    public void setDislikenumber(int dislikenumber) {
        this.dislikenumber = dislikenumber;
    }

    public int getLikenumber() {
        return likenumber;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }
    public boolean getuserlike()  {
        return userlike;
    }
    public boolean getuserdislike()  {
        return userdislike;
    }
}
