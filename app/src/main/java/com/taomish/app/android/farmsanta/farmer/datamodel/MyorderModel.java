package com.taomish.app.android.farmsanta.farmer.datamodel;

public class MyorderModel {
  private String OrderID, Date, orderNums, orderPrice, orderProducts, OrderCheck,Name,Contact,Address;

    public MyorderModel(String OrderId, String date, String orderNums, String orderPrice, String orderProducts, String OrderCheck, String name, String contact, String address) {
        this.OrderID = OrderId;
        Date = date;
        this.orderNums = orderNums;
        this.orderPrice = orderPrice;
        this.orderProducts = orderProducts;
        this.OrderCheck = OrderCheck;
        this.Name=name;
        this.Contact=contact;
        this.Address=address;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getOrderNums() {
        return orderNums;
    }

    public void setOrderNums(String orderNums) {
        this.orderNums = orderNums;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(String orderProducts) {
        this.orderProducts = orderProducts;
    }

    public String getOrderCheck() {
        return OrderCheck;
    }

    public void setOrderCheck(String orderCheck) {
        OrderCheck = orderCheck;
    }

    public String getOrderID() {
        return OrderID;
    }
    public String getName() {
        return Name;
    }
    public String getContact() {
        return Contact;
    }
    public String getAddress() {
        return Address;
    }
    public void setOrderID(String orderID) {
        OrderID = orderID;
    }
}
