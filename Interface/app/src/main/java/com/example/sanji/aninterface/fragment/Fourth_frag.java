package com.example.sanji.aninterface.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.sanji.aninterface.fragmentView.fourthView;
import com.example.sanji.aninterface.views.Obj3DView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian-polymorse on 3/17/18.
 */

@SuppressLint("ValidFragment")
public class Fourth_frag extends Fragment {
    private Client client =  null;
    private TabHost mTabHost;
    View V;
    private List<String> tabID = new ArrayList<>();

    Obj3DView view;
    private List<TabHost.TabSpec> lidtWidget = new ArrayList<TabHost.TabSpec>();
    public Fourth_frag(Client tab){
        client = tab;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        V = inflater.inflate(R.layout.fourth_fragment, null, false);;
        mTabHost = (TabHost)V.findViewById(R.id.tabHost4);
        creating();
        return V;
    }
    public void creating(){
        System.out.println(mTabHost);
        mTabHost.setup();
        mTabHost.setCurrentTab(0);

        for (int i = 0; i < client.listTotalWidget.get(3).size(); i++) {
            lidtWidget.add(mTabHost.newTabSpec(client.listTotalWidget.get(3).get(i)));

        }
        for (int j = 0; j < lidtWidget.size(); j++) {
            lidtWidget.get(j).setIndicator( lidtWidget.get(j).getTag());

            lidtWidget.get(j).setContent(new DummyTabContent(getActivity()));
            mTabHost.setCurrentTab(0);
            mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {
                    tabID.add(tabId);

                    //Toast.makeText(getActivity(), tabId, Toast.LENGTH_SHORT).show();

                    //for(int i = 0; i< lidtWidget.size(); i++){
                        TextView temp  = new TextView(getActivity());
                        GradientDrawable gd = new GradientDrawable();
                        LinearLayout.LayoutParams layoutParams1;
                        FragmentManager fragmentManager = getFragmentManager();
                     //   Fragment fragment = new fourthView(null, null,null);

                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                        FragmentTransaction replace = null;
                    if(tabId.matches(lidtWidget.get(0).getTag())){
                        Fragment fragmentS = new fourthView(client.listJsonLastTabWid2D1, client.listJsonLastTabWid2D2,client);
                        FrameLayout fr = mTabHost.getTabContentView();
                        fr.setId(R.id.layout4);
                        fr.removeAllViews();
                        replace = transaction.add(R.id.layout4, fragmentS);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        //break;
                    }
                    if(lidtWidget.size() > 1 && tabId.matches(lidtWidget.get(1).getTag())){
                        FrameLayout fre = mTabHost.getTabContentView();
                        fre.setId(R.id.layout4);
                        fre.removeAllViews();
                        view = new Obj3DView();
                        Intent myIntent = new Intent(getActivity(), Obj3DView.class);
                        getActivity().startActivity(myIntent);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        //break;
                    }

                   // }
                }
            });
          /*  TextView temp  = new TextView(getActivity());
            GradientDrawable gd = new GradientDrawable();
            LinearLayout.LayoutParams layoutParams1;
            FragmentManager fragmentManager = getFragmentManager();
            //   Fragment fragment = new fourthView(null, null,null);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            FragmentTransaction transaction1 = fragmentManager.beginTransaction();
            FragmentTransaction replace = null;
            if(tabID.get(0).matches(lidtWidget.get(0).getTag())){
                FrameLayout fre = mTabHost.getTabContentView();
                fre.setId(R.id.layout3);
                fre.removeAllViews();
                // transaction.add(R.id.layout3, fragmentMap);
                transaction.commit();

                //break;
            }
            if(tabID.get(1).matches(lidtWidget.get(1).getTag())){
                FrameLayout fre = mTabHost.getTabContentView();
                fre.setId(R.id.layout3);
                fre.removeAllViews();
                view = new Obj3DView();
                Intent myIntent = new Intent(getActivity(), Obj3DView.class);
                getActivity().startActivity(myIntent);
                transaction.addToBackStack(null);
                transaction.commit();
                //break;
            }*/
           /* for(int i=0;i<tabID.size();i++) {
                for (int a = 0; a < lidtWidget.size(); a++){
                    if(tabID.get(i).matches(lidtWidget.get(a).getTag())){
                        Fragment fragmentS = new fourthView(client.listJsonLastTabWid2D1, client.listJsonLastTabWid2D2,client);
                        FrameLayout fr = mTabHost.getTabContentView();
                        fr.setId(R.id.layout4);
                        fr.removeAllViews();
                        replace = transaction.add(R.id.layout4, fragmentS);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        //break;
                    }
                }
            }*/

            mTabHost.addTab(lidtWidget.get(j));
        }
    }
}
