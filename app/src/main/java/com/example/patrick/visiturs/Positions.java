package com.example.patrick.visiturs;

/**
 * Created by Patrick on 19-04-2017.
 */

public class Positions {
    double lon;
    double lat;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Positions(double lon, double lat) {

        this.lon = lon;
        this.lat = lat;
    }
}
