package com.taomish.app.android.farmsanta.farmer.datamodel;

import java.util.ArrayList;

public class ProductbyShop_Data {
    String shopname,shopid,productname,quatity,mrp,price,discount,veriants,type;
    int shopimage,productimage;
    private String noofuser;
    private String rating;
    boolean bookmark,bestseller;
    private ArrayList<ProductbyShop_Data> singleItemModelArrayList;
    String id;

    public ProductbyShop_Data(String id,String shopname,String rating, String noofuser,String shopid,String type,String productname,String quatity,String mrp,String price,String discount,String veriants,int shopimage,int productimage,boolean bookmark,boolean bestseller) {
       this.id=id;
        this.shopname = shopname;
        this.shopid = shopid;
        this.rating=rating;
        this.noofuser=noofuser;
        this.productname = productname;
        this.quatity = quatity;
        this.mrp = mrp;
        this.price = price;
        this.discount = discount;
        this.veriants = veriants;
        this.shopimage = shopimage;
        this.productimage = productimage;
        this.bookmark = bookmark;
        this.bestseller = bestseller;
        this.type=type;
    }
    public ProductbyShop_Data(String title, String rating, String noofuser, ArrayList<ProductbyShop_Data> singleItemModelArrayList) {
        this.shopname = title;
        this.rating=rating;
        this.noofuser=noofuser;
        this.singleItemModelArrayList = singleItemModelArrayList;
    }

    public String getId() {
        return id;
    }

    public int getShopimage() {
        return shopimage;
    }

    public int getProductimage() {
        return productimage;
    }

    public String getDiscount() {
        return discount;
    }

    public String getMrp() {
        return mrp;
    }

    public String getPrice() {
        return price;
    }

    public String getProductname() {
        return productname;
    }

    public String getQuatity() {
        return quatity;
    }

    public String getShopid() {
        return shopid;
    }

    public String getShopname() {
        return shopname;
    }

    public String getVeriants() {
        return veriants;
    }

    public String getNoofuser() {
        return noofuser;
    }

    public String getRating() {
        return rating;
    }
    public Boolean getBookmark() {
        return bookmark;
    }
    public Boolean getBestSeller() {
        return bestseller;
    }
    public String getType() {
        return type;
    }

    public ArrayList<ProductbyShop_Data> getSingleItemModelArrayList() {
        return singleItemModelArrayList;
    }
}
