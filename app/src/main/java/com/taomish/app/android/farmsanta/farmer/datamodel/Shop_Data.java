package com.taomish.app.android.farmsanta.farmer.datamodel;

public class Shop_Data {
    private String shoptitle;
    public int shopimage;
    private String shoprating;
    private String shopaddress;
    private String nearest;
    private String productsno;
    private String productlist;

    public Shop_Data(String shoptitle,int image,String shoprating, String shopaddress,String nearest,String productsno,String productlist) {
        this.shoptitle = shoptitle;
        this.shopimage=image;
        this.shoprating=shoprating;
        this.shopaddress=shopaddress;
        this.nearest=nearest;
        this.productsno=productsno;
        this.productlist=productlist;
    }

    public String getShoptitle() {
        return shoptitle;
    }

    public int getShopimage() {
        return shopimage;
    }

    public String getNearest() {
        return nearest;
    }

    public String getProductlist() {
        return productlist;
    }

    public String getProductsno() {
        return productsno;
    }

    public String getShoprating() {
        return shoprating;
    }

    public String getShopaddress() {
        return shopaddress;
    }
}
