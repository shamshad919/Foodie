package com.example.shamshad.foodorder;



public class food_details {
    String text;
    String image;
    String foodid;

    public food_details(String text, String image, String foodid) {
        this.text = text;
        this.image = image;
        this.foodid = foodid;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
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
}
