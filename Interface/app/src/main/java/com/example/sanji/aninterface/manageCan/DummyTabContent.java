package com.example.sanji.aninterface.manageCan;

/**
 * Created by christian-polymorse on 3/11/18.
 */

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

public class DummyTabContent implements TabContentFactory{
    private Context mContext;

    public DummyTabContent(Context context){
        mContext = context;
    }

    @Override
    public View createTabContent(String tag) {
        View v = new View(mContext);
        return v;
    }
}
