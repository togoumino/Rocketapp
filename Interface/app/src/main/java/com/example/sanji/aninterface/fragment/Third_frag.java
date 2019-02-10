package com.example.sanji.aninterface.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.sanji.aninterface.rest.Client;
import com.example.sanji.aninterface.manageCan.DummyTabContent;
import com.example.sanji.aninterface.R;
import com.example.sanji.aninterface.fragmentView.ThirdView;
import com.example.sanji.aninterface.views.ViewMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian-polymorse on 3/17/18.
 */
@SuppressLint("ValidFragment")
public class Third_frag extends Fragment {

    private Client client =  null;
    private TabHost mTabHost;
    View V;

    private List<TabHost.TabSpec> lidtWidget = new ArrayList<TabHost.TabSpec>();
    public Third_frag(Client tab){
        client = tab;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        V = inflater.inflate(R.layout.third_fragment, null, false);;
        mTabHost = (TabHost)V.findViewById(R.id.tabHost3);
        creating();
        return V;
    }
    public void creating(){
        System.out.println(mTabHost);
        mTabHost.setup();
        mTabHost.setCurrentTab(0);
        for (int i = 0; i < client.listTotalWidget.get(2).size(); i++) {
            lidtWidget.add(mTabHost.newTabSpec(client.listTotalWidget.get(2).get(i)));

        }


        for (int j = 0; j < lidtWidget.size(); j++) {
            lidtWidget.get(j).setIndicator( lidtWidget.get(j).getTag());

            lidtWidget.get(j).setContent(new DummyTabContent(getActivity()));
            mTabHost.setCurrentTab(0);
            mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {
                   // Toast.makeText(getActivity(), tabId, Toast.LENGTH_SHORT).show();

                        TextView temp  = new TextView(getActivity());
                        GradientDrawable gd = new GradientDrawable();
                        LinearLayout.LayoutParams layoutParams1;
                        FragmentManager fragmentManager = getFragmentManager();
                        Fragment fragment1 = new ThirdView(null);
                        Fragment fragmentMap = new ViewMap();

                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                        FragmentTransaction replace = null;
                    if(tabId.matches(lidtWidget.get(0).getTag())){
                        FrameLayout fre = mTabHost.getTabContentView();
                        fre.setId(R.id.layout3);
                        fre.removeAllViews();
                        transaction.add(R.id.layout3, fragmentMap);
                        transaction.commit();

                        //break;
                    }
                    if(tabId.matches(lidtWidget.get(1).getTag())){
                        FrameLayout fre = mTabHost.getTabContentView();
                        fre.setId(R.id.layout3);
                        fre.removeAllViews();
                        transaction.addToBackStack(null);
                        transaction.commit();
                        //break;
                    }
                    if(tabId.matches(lidtWidget.get(2).getTag())){
                        FrameLayout fre = mTabHost.getTabContentView();
                        fre.setId(R.id.layout3);
                        fre.removeAllViews();
                        // transaction.hide(fragment).commit();
                        //break;
                    }

                }
            });
            mTabHost.addTab(lidtWidget.get(j));
        }
    }

}
