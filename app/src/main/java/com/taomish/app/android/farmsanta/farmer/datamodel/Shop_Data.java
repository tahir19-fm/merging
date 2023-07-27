package com.taomish.app.android.farmsanta.farmer.datamodel;

public class Shop_Data {
    private final String shoptitle;
    public int shopimage;
    private final String shoprating;
    private final String shopaddress;
    private final String nearest;
    private final String productsno;
    private final String productlist;

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
