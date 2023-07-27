package com.taomish.app.android.farmsanta.farmer.datamodel;

public class Cart_Data {
    private String productname;
    private int unit;
    private String price;
    private int stock;
    private int productimg;

    public Cart_Data(){

    }
    public Cart_Data(String productname,int stock,String price,int unit, int productimg){
        this.productname = productname;
        this.stock = stock;
        this.price = price;
        this.unit = unit;
        this.productimg = productimg;
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

    public int getStock() {
        return stock;
    }

    public int getUnit() {
        return unit;
    }
}
