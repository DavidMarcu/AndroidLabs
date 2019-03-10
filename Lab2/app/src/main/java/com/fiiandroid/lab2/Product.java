package com.fiiandroid.lab2;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String name;
    private double price;
    private String desciption;
    private int imageResource;

    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    private Product(Parcel inputParcel){
        name = inputParcel.readString();
        price = inputParcel.readDouble();
        desciption = inputParcel.readString();
        imageResource = inputParcel.readInt();
    }

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



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(price);
        parcel.writeString(desciption);
        parcel.writeInt(imageResource);
    }
}
