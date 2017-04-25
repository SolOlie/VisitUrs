package com.example.patrick.visiturs;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Patrick on 07-03-2017.
 */

public class Location implements Serializable {
    String name;
    String address;
    String zipcode;
    String number;
    String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    String phoneNumber;
    int id;
    double lat;
    double lon;


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }


    public Location(String name, String address, String zipcode, String number, String phoneNumber, int id, String email) {
        this.name = name;
        this.address = address;
        this.zipcode = zipcode;
        this.number = number;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.email = email;
    }

    String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }
}
