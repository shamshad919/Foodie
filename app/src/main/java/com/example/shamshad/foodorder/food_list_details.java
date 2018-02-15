package com.example.shamshad.foodorder;


import com.example.shamshad.foodorder.Interface.ItemClickListener;

public class food_list_details {
    String text;
    String image;
    String price;
    String quantity;
    String food_id;

    ItemClickListener itemClickListener;

    public food_list_details() {
    }

    public food_list_details(String text, String image, String price, String quantity, String food_id) {
        this.text = text;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.food_id = food_id;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public String getText() {
        return text;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }
}
