package com.example.shamshad.foodorder;

/**
 * Created by shamshad on 16/1/18.
 */

public class restaurant_details {
     String name;
     String image;

    public restaurant_details() {
    }

    public restaurant_details(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
