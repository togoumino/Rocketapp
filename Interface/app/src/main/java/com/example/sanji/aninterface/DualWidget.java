package com.example.sanji.aninterface;

import java.util.List;
public class DualWidget {
    private List<DataDisplayer> dataDisplayerList;
    private boolean isHorizontal;
    public DualWidget(List<DataDisplayer> dataDisplayerList, boolean isHorizontal) {
        this.dataDisplayerList = dataDisplayerList;
        this.isHorizontal = isHorizontal;
    }
    public List<DataDisplayer> getDataDisplayerList() {
        return dataDisplayerList;
    }
    public void setDataDisplayerList(List<DataDisplayer> dataDisplayerList) {
        this.dataDisplayerList = dataDisplayerList;
    }
    public boolean isHorizontal() {
        return isHorizontal;
    }
    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }
}
