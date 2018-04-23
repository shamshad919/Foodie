package com.example.shamshad.foodorder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoro on 23-Apr-18.
 */

public class requestOrder {
    public String Adres;
    public String pushidkey;

    public String getAdres() {
        return Adres;
    }

    public void setAdres(String adres) {
        Adres = adres;
    }

    public String getPushidkey() {
        return pushidkey;
    }

    public void setPushidkey(String pushidkey) {
        this.pushidkey = pushidkey;
    }

    public requestOrder(String Adres, String pushidkey) {
        this.Adres = Adres;
        this.pushidkey = pushidkey;
    }

    public requestOrder() {
    }


    public requestOrder(String Address) {
        this.Adres = Adres;
    }

    public String getAddress() {
        return Adres;
    }

    public void setAddress(String Address) {
        this.Adres = Adres;
    }
}
