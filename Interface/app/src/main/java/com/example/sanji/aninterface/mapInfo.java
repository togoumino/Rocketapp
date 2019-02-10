package com.example.sanji.aninterface;

public class mapInfo {
    public String map;
    public int themeID;
    private static final mapInfo ourInstance = new mapInfo();

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public static mapInfo getInstance() {
        return ourInstance;
    }
    private mapInfo() {
    }
}
