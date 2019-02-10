package com.example.sanji.aninterface.manageCan;

public class CANConfig {
    private String name;
    private String id;
    private String display;
    private double minAcceptable;
    private double maxAcceptable;
    private int chiffresSign;
    private String specificSource;
    private int serialNB;
    private String value;
    public CANConfig() {
        name = "";
        id = "";
        display = "";
        minAcceptable = 0;
        maxAcceptable = 0;
        chiffresSign = 0;
        specificSource = "";
        serialNB = 0;
        value = "";
    }
    public CANConfig(CANConfig copy) {
        name = copy.getName();
        id = copy.getId();
        display = copy.getDisplay();
        minAcceptable = copy.minAcceptable;
        maxAcceptable = copy.maxAcceptable;
        chiffresSign = copy.getChiffresSign();
        specificSource = copy.getSpecificSource();
        serialNB = copy.getSerialNB();
        value = copy.getValue();
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return "CANConfig{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", display='" + display + '\'' +
                ", minAcceptable=" + minAcceptable +
                ", maxAcceptable=" + maxAcceptable +
                ", chiffresSign=" + chiffresSign +
                ", specificSource='" + specificSource + '\'' +
                ", serialNB=" + serialNB +
                '}';
    }
    public void updateByAttribute(String attribute, String val) {
        if (attribute.equalsIgnoreCase("name")) {
            this.setName(val);
        } else if (attribute.equalsIgnoreCase("id")) {
            this.setId(val);
        } else if (attribute.equalsIgnoreCase("display")) {
            this.setDisplay(val);
        } else if (attribute.equalsIgnoreCase("minAcceptable")) {
            this.setMinAcceptable(Double.parseDouble(val));
        } else if (attribute.equalsIgnoreCase("maxAcceptable")) {
            this.setMaxAcceptable(Double.parseDouble(val));
        } else if (attribute.equalsIgnoreCase("chiffresSign")) {
            this.setChiffresSign(Integer.parseInt(val));
        } else if (attribute.equalsIgnoreCase("specificSource")) {
            this.setSpecificSource(val);
        } else if (attribute.equalsIgnoreCase("serialNB")) {
            this.setSerialNB(Integer.parseInt(val));
        } else {
            System.err.println("Detected invalid attribute\t" + attribute);
        }
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDisplay() {
        return display;
    }
    public void setDisplay(String display) {
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
    public int getSerialNB() {
        return serialNB;
    }
    public void setSerialNB(int serialNB) {
        this.serialNB = serialNB;
    }
}
