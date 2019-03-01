package com.fiiandroid.lab2;

public class Product {
    private String name;
    private double price;
    private String desciption;
    private int imageResource;

    public Product(String name, double price, String desciption, int imageResource){
        this.name = name;
        this.desciption = desciption;
        this.price = price;
        this.imageResource = imageResource;
    }

    public int getImageResource(){
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDesciption() {
        return desciption;
    }

}
