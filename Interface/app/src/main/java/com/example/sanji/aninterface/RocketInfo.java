package com.example.sanji.aninterface;

public class RocketInfo {
    private static final RocketInfo ourInstance = new RocketInfo();
    public Double lat, lon;

   public static RocketInfo getInstance() {
        return ourInstance;
    }

    public static RocketInfo getOurInstance() {
        return ourInstance;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    private RocketInfo() {

    }
}
