package com.example.sanji.aninterface.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TabHost;

import com.example.sanji.aninterface.rest.Client;
import com.example.sanji.aninterface.manageCan.DummyTabContent;
import com.example.sanji.aninterface.fragmentView.FirstView;
import com.example.sanji.aninterface.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian-polymorse on 3/15/18.
 */

@SuppressLint("ValidFragment")
public class First_frag extends  Fragment {
   private Client client =  null;
   private TabHost mTabHost5;
   private int Tabnu, indice = 0;
    Runnable updater;
private String tabID = "";
    Fragment fragment = null;
    Fragment fragmentS = null;
    View V;
   private List<TabHost.TabSpec> listId = new ArrayList<TabHost.TabSpec>();
    First_frag(){}

    public First_frag(Client tab){
        client = tab;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        V = inflater.inflate(R.layout.first_fragment, null, false);

        mTabHost5 = V.findViewById(R.id.tabHost1);

        Creat();


        return V;
    }

    public void Creat(){
        mTabHost5.setup();
        mTabHost5.setCurrentTab(0);
        for (int i = 0; i < client.listTotalWidget.get(0).size(); i++) {
            listId.add(mTabHost5.newTabSpec(client.listTotalWidget.get(0).get(i)));

        }

        for (int j = 0; j < listId.size(); j++) {
            listId.get(j).setIndicator( listId.get(j).getTag());

            listId.get(j).setContent(new DummyTabContent(getActivity()));
            //mTabHost5.setCurrentTab(0);
            mTabHost5.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {
                    tabID = tabId;

                  // Toast.makeText(getActivity(), tabId, Toast.LENGTH_SHORT).show();
                    Tabnu = mTabHost5.getTabWidget().getTabCount();
                    FragmentManager fragmentManager = getFragmentManager();

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    fragment = new FirstView(client.listJsonFirstTabD1, client.listJsonFirstTabD2, client);
                    fragmentS = new FirstView(client.listJsonFirstTabWid2D1, client.listJsonFirstTabWid2D2,client);

                    if(tabID.matches(listId.get(0).getTag())){

                        FrameLayout fr = mTabHost5.getTabContentView();
                        fr.setId(R.id.layout1);
                        fr.removeAllViews();
                        transaction.add(R.id.layout1, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    }
                    if(tabID.matches(listId.get(1).getTag())){

                        FrameLayout frd = mTabHost5.getTabContentView();
                        frd.setId(R.id.layout1);
                        frd.removeAllViews();
                        transaction.add(R.id.layout1, fragmentS);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    }
                    if(tabID.matches(listId.get(2).getTag())){
                        FrameLayout fre = mTabHost5.getTabContentView();
                        fre.setId(R.id.layout1);
                        fre.removeAllViews();

                    }

                }
            });

            mTabHost5.addTab(listId.get(j));
        }
    }
    public int getSize(){
        return  client.TabName.size();
    }
}
