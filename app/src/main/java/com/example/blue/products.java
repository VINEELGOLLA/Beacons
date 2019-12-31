package com.example.blue;

public class products {

    String name;
    Double price;
    String image;
    int discount;

    public products() {
    }

    public products(String name, Double price, String image, int discount) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
