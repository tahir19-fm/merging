package com.taomish.app.android.farmsanta.farmer.datamodel;

public class Product {
    public String quantity , price , expired , image;

    public Product(String quantity, String price, String expired, String image) {
        this.quantity = quantity;
        this.price = price;
        this.expired = expired;
        this.image = image;
    }
}
