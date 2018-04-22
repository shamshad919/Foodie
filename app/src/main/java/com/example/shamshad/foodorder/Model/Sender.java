package com.example.shamshad.foodorder.Model;

/**
 * Created by Zoro on 21-Apr-18.
 */

public class Sender {
    public String to;
    public Notification notification;

    public Sender(String to, Notification notification) {
        this.to = to;
        this.notification = notification;
    }


}
