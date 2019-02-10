package com.example.sanji.aninterface.messageFormat;

public class LayoutMessage {
    private String name;
    private String id;
    private double display;
    private double minAcceptable;
    private double maxAcceptable;
    private int chiffresSign;
    private String specificSource;
    private int serialNb;
    @Override
    public String toString() {
        return "LayoutMessage{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", display=" + display +
                ", minAcceptable=" + minAcceptable +
                ", maxAcceptable=" + maxAcceptable +
                ", chiffresSign=" + chiffresSign +
                ", specificSource=" + specificSource +
                ", serialNb='" + serialNb + '\'' +
                '}';
    }
    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public double getDisplay() { return display;   }
    public void setDisplay(double display) {
        this.display = display;
    }
    public double getMinAcceptable() {
        return minAcceptable;
    }
    public void setMinAcceptable(double minAcceptable) {
        this.minAcceptable = minAcceptable;
    }
    public double getMaxAcceptable() {
        return maxAcceptable;
    }
    public void setMaxAcceptable(double maxAcceptable) {
        this.maxAcceptable = maxAcceptable;
    }
    public int getChiffresSign() {
        return chiffresSign;
    }
    public void setChiffresSign(int chiffresSign) {
        this.chiffresSign = chiffresSign;
    }
    public String getSpecificSource() {
        return specificSource;
    }
    public void setSpecificSource(String specificSource) {
        this.specificSource = specificSource;
    }
    public int getSerialNb() {
        return serialNb;
    }
    public void setSerialNb(int serialNb) {
        this.serialNb = serialNb;
    }

}
