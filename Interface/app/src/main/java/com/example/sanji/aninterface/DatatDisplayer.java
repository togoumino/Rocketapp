package com.example.sanji.aninterface;

import com.example.sanji.aninterface.manageCan.CANConfig;

import java.util.List;
class DataDisplayer {
    private List<CANConfig> cansList;
    public List<CANConfig> getCansList() {
        return cansList;
    }
    public void setCansList(List<CANConfig> cansList) {
        this.cansList = cansList;
    }
    public DataDisplayer(List<CANConfig> cansList) {
        this.cansList = cansList;
    }
}