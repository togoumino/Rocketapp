package com.example.sanji.aninterface.fragmentView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sanji.aninterface.rest.Client;
import com.example.sanji.aninterface.messageFormat.LayoutMessage;
import com.example.sanji.aninterface.R;
import com.example.sanji.aninterface.views.TView;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class FirstView extends Fragment {

    private Client client = null;
    List<LayoutMessage> firstDTab = new ArrayList<LayoutMessage>();
    List<LayoutMessage> secondDTab = new ArrayList<LayoutMessage>();
    List<LinearLayout> canTable = new ArrayList<LinearLayout>();
    List<TextView> ITEM_LIST = new ArrayList<>();
    List<TextView> ITEM_LIST_SECOND_VIEW = new ArrayList<>();
    List<String> listID = new ArrayList<>();
    List<String> listID2 = new ArrayList<>();

    TView arrayadapter;
    TView secondArrayadapter;
    Client cl;

    public FirstView(List<LayoutMessage> f, List<LayoutMessage> s, Client c) {
        firstDTab = f;
        secondDTab = s;
        cl = c;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.first_fragment, null, false);
        GridView firstTab = V.findViewById(R.id.linearLayout1);
        GridView secondTab = V.findViewById(R.id.linearLayout2);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(10);
        gd.setStroke(1, Color.RED);
        firstTab.setBackground(gd);
        secondTab.setBackground(gd);
        final Handler refreshHandler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // do updates for imageview
                ITEM_LIST = new ArrayList<TextView>();

                for(int j = 0; j< firstDTab.size();++j){
                    TextView temp  = new TextView(getActivity());
                    TextView displayTex  = new TextView(getActivity());
                    //
                    try {
                        temp.setText(firstDTab.get(j).getName());
                        listID.add(firstDTab.get(j).getId());
                        displayTex.setText(round(String.valueOf(firstDTab.get(j).getDisplay()),firstDTab.get(j).getChiffresSign()));

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    ITEM_LIST.add(temp);
                    ITEM_LIST.add(displayTex);

                }
                arrayadapter = new TView(V.getContext(),ITEM_LIST,firstDTab,listID);
                firstTab.setAdapter(arrayadapter);

                ITEM_LIST_SECOND_VIEW = new ArrayList<TextView>();
                for(int j = 0; j< secondDTab.size();j++){
                    TextView temp  = new TextView(getActivity());
                    TextView displayTex  = new TextView(getActivity());

                    try {
                        listID2.add(secondDTab.get(j).getId());
                        //if(secondDTab.get(j).getJSONObject(0).has("name"))
                        temp.setText(secondDTab.get(j).getName());
                        // if(secondDTab.get(j).getJSONObject(0).has("display"))
                        displayTex.setText(String.valueOf(secondDTab.get(j).getDisplay()));

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    ITEM_LIST_SECOND_VIEW.add(temp);
                    ITEM_LIST_SECOND_VIEW.add(displayTex);
                }
                secondArrayadapter = new TView(V.getContext(),ITEM_LIST_SECOND_VIEW,secondDTab,listID2);
                secondTab.setAdapter(secondArrayadapter);

                refreshHandler.postDelayed(this,  4000);
            }
        };
        refreshHandler.postDelayed(runnable,  2000);


        return V;
    }
    public static String round(String value, int chiffreSign) {
        if (chiffreSign != 0)
            return value.length() >= chiffreSign ? value.substring(0, chiffreSign) : applyPadding(value, chiffreSign);
        else
            return value;
    }
    private static String applyPadding(String val, int len) {
        for (int i = 0; i < len - val.length(); i++) {
            val += '0';
        }
        return val;
    }
}

