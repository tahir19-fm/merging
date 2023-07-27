package com.taomish.app.android.farmsanta.farmer.datamodel;

import java.util.ArrayList;

public class Seller_Product_Data {
    int productimg;
    String productid,productname,quantity,price,variants,rating,ratingusers;
    private ArrayList<Seller_Product_Data> singleItemModelArrayList;

    public Seller_Product_Data(String productid,int productimg,String productname,String quantity,String price,String variants,String rating,String ratingusers) {
        this.productimg = productimg;
        this.productid=productid;
        this.productname=productname;
        this.quantity = quantity;
        this.price=price;
        this.variants=variants;
        this.rating = rating;
        this.ratingusers=ratingusers;

    }

    public Seller_Product_Data(String productname,  ArrayList<Seller_Product_Data> singleItemModelArrayList) {
        this.productname = productname;
        this.singleItemModelArrayList = singleItemModelArrayList;
    }

    public String getProductname() {
        return productname;
    }

    public String getProductid() {
        return productid;
    }

    public String getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }

    public int getProductimg() {
        return productimg;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getRatingusers() {
        return ratingusers;
    }

    public String getVariants() {
        return variants;
    }

    public ArrayList<Seller_Product_Data> getSingleItemModelArrayList() {
        return singleItemModelArrayList;
    }
}
