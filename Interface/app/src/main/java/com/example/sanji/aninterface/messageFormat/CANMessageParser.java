package com.example.sanji.aninterface.messageFormat;

import com.example.sanji.aninterface.messageFormat.CANMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class CANMessageParser {
    public static CANMessage fromString(String can) {
        CANMessage m = new CANMessage();
        try {
            JSONObject jsonCan = new JSONObject(can);
            m.setSid(jsonCan.getString("sid"));
            m.setSrcType(jsonCan.getInt("src_type"));
            m.setSrcSerial(jsonCan.getInt("src_serial"));
            m.setDestType(jsonCan.getInt("dest_type"));
            m.setDestSerial(jsonCan.getInt("dest_serial"));
            m.setData1(Double.parseDouble(jsonCan.getString("data_1")));
            try{
                m.setData2(Float.valueOf(jsonCan.getString("data_2")));}
            catch(NumberFormatException ex) { }
            m.setCrc(jsonCan.getString("crc"));
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return m;
        }
    }
}
