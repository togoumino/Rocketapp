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

/**
 * Created by christian-polymorse on 3/17/18.
 */

@SuppressLint("ValidFragment")
public class fourthView extends Fragment {

    private Client client = null;
    List<LayoutMessage> firstDTab = new ArrayList<LayoutMessage>();
    List<LayoutMessage> secondDTab = new ArrayList<LayoutMessage>();
    Client cl;

    int leftMargin = 10;
    int topMargin = 30;
    int rightMargin = 0;
    int bottomMargin = 0;
    int rownum = 3;
    List<LinearLayout> canTable = new ArrayList<LinearLayout>();
    List<TextView> ITEM_LIST,ITEM_LIST_SECOND;
    List<String> listID = new ArrayList<>();
    List<String> listID2 = new ArrayList<>();

    TView arrayadapter, arrayadapter_second;
    int width = 250;

    int height = 65;
    int widthP = 200;

    public fourthView(List<LayoutMessage> f, List<LayoutMessage> s, Client c) {
        firstDTab = f;
        secondDTab = s;
        cl = c;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fourth_fragment, container, false);
        GridView firstTab = V.findViewById(R.id.linearLayout7);
        GridView secondTab = V.findViewById(R.id.linearLayout6);

        LinearLayout.LayoutParams layoutParams1;
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(2, Color.BLACK);
        firstTab.setBackground(gd);
        secondTab.setBackground(gd);
        final Handler refreshHandler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // do updates for imageview
                ITEM_LIST = new ArrayList<TextView>();
                List<String> values = new ArrayList<String>();
                for(int j = 0; j< firstDTab.size();j++){
                    TextView temp  = new TextView(getActivity());
                    TextView displayTex  = new TextView(getActivity());

                    try {
                        listID.add(firstDTab.get(j).getId());
                        temp.setText(firstDTab.get(j).getName());
                        if(firstDTab.get(j).getName()!=null )
                            displayTex.setText(String.valueOf(firstDTab.get(j).getDisplay()));
               /* if(firstDTab.get(j).getJSONObject(0).has("name"))
                    temp.setText(firstDTab.get(j).getJSONObject(0).get("name").toString());
                if(firstDTab.get(j).getJSONObject(0).has("display"))
                    displayTex.setText(firstDTab.get(j).getJSONObject(0).get("display").toString());*/
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    ITEM_LIST.add(temp);
                    ITEM_LIST.add(displayTex);
                }
                arrayadapter = new TView(V.getContext(),ITEM_LIST,firstDTab,listID);
                firstTab.setAdapter(arrayadapter);

                ITEM_LIST_SECOND = new ArrayList<TextView>();
                List<String> value = new ArrayList<String>();
                for(int j = 0; j< secondDTab.size();j++){
                    TextView temp  = new TextView(getActivity());
                    TextView displayTex  = new TextView(getActivity());

                    try {
                        listID2.add(secondDTab.get(j).getId());
                        temp.setText(secondDTab.get(j).getName());
                        if(secondDTab.get(j).getName()!=null )
                            displayTex.setText(String.valueOf(secondDTab.get(j).getDisplay()));

               /* if(secondDTab.get(j).getJSONObject(0).has("name"))
                    temp.setText(secondDTab.get(j).getJSONObject(0).get("name").toString());
                if(secondDTab.get(j).getJSONObject(0).has("display"))
                    displayTex.setText(secondDTab.get(j).getJSONObject(0).get("display").toString());*/
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }


                    ITEM_LIST_SECOND.add(temp);
                    ITEM_LIST_SECOND.add(displayTex);

                }
                arrayadapter_second = new TView(V.getContext(),ITEM_LIST_SECOND,secondDTab,listID2);
                secondTab.setAdapter(arrayadapter_second);

                refreshHandler.postDelayed(this,  4000);
            }
        };
        refreshHandler.postDelayed(runnable,  2000);


        return V;
    }
}

