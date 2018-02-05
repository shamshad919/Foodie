package com.example.shamshad.foodorder;



public class food_details {
    String text;
    String image;
    String price;

    public food_details() {
    }

    public food_details(String text, String image, String price) {
        this.text = text;
        this.image = image;
        this.price = price;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
