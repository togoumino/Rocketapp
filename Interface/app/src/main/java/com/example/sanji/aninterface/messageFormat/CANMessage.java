package com.example.sanji.aninterface.messageFormat;

public class CANMessage {
    private String sid;
    private int destSerial;
    private int destType;
    private int srcSerial;
    private int srcType;
    private double data1;
    private double data2;
    private String crc;
    @Override
    public String toString() {
        return "CANMessage{" +
                "sid='" + sid + '\'' +
                ", destSerial=" + destSerial +
                ", destType=" + destType +
                ", srcSerial=" + srcSerial +
                ", srcType=" + srcType +
                ", data1=" + data1 +
                ", data2=" + data2 +
                ", crc='" + crc + '\'' +
                '}';
    }
    public String getSid() {
        return sid;
    }
    public void setSid(String sid) {
        this.sid = sid;
    }
    public int getDestSerial() {
        return destSerial;
    }
    public void setDestSerial(int destSerial) {
        this.destSerial = destSerial;
    }
    public int getDestType() {
        return destType;
    }
    public void setDestType(int destType) {
        this.destType = destType;
    }
    public int getSrcSerial() {
        return srcSerial;
    }
    public void setSrcSerial(int srcSerial) {
        this.srcSerial = srcSerial;
    }
    public int getSrcType() {
        return srcType;
    }
    public void setSrcType(int srcType) {
        this.srcType = srcType;
    }
    public double getData1() {
        return data1;
    }
    public void setData1(double data1) {
        this.data1 = data1;
    }
    public double getData2() {
        return data2;
    }
    public void setData2(double data2) {
        this.data2 = data2;
    }
    public String getCrc() {
        return crc;
    }
    public void setCrc(String crc) {
        this.crc = crc;
    }
}
