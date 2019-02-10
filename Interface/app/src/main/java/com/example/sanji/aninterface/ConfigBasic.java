package com.example.sanji.aninterface;

public class ConfigBasic {
    public static String IP_ADDRESS = "10.200.13.241";
    public static int REST_API_PORT = 3000;
    public static int SOCKET_PORT = 5000;
    public String layout;
    public String map;
    public int otherPort;
    public String getLayout() {
        return layout;
    }
    public void setLayout(String layout) {
        this.layout = layout;
    }
    public String getMap() {
        return map;
    }
    public void setMap(String map) {
        this.map = map;
    }
    public int getOtherPort() {
        return otherPort;
    }
    public void setOtherPort(int otherPort) {
        this.otherPort = otherPort;
    }
}
