package com.taomish.app.android.farmsanta.farmer.datamodel;


import android.app.Activity;

public class SoilExpert_list_data {
    private String imageId,type2;
    private String category,name,minfees, maxfees,statename,id,doctorid,clinicid,backpath;
    private Float rating;
    private Activity activity;
    private int type;


    public String getImageId() {
        return imageId;
    }
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getbackpath() { return backpath; }
    public void setbackpath(String backpath) {
        this.backpath = backpath;
    }
    public String getRating() { return String.valueOf(rating); }
    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getMinFees() { return minfees; }
    public void setMinFees(String minfees) { this.minfees = minfees; }

    public String getClinicId() {
        return clinicid;
    }
    public void setClinicId(String clinicid) {
        this.clinicid = clinicid;
    }

    public String getMaxFees() {
        return maxfees;
    }
    public void setMaxFees(String maxfees) {
        this.maxfees = maxfees;
    }

    public Activity getActivity() {
        return activity;
    }
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }
    public String getType2() {
        return type2;
    }


    public String getid() {
        return id;
    }
    public void setid(String id) {
        this.id = id;
    }

    public String getDoctorid() {
        return doctorid;
    }
    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public void setStateName(String statename) {
        this.statename = statename;
    }
    public String getStateName() {
        return statename;
    }


  /*
   public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }*/
}
