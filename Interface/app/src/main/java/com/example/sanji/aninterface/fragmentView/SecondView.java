package com.example.sanji.aninterface.fragmentView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.sanji.aninterface.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian-polymorse on 3/11/18.
 */

@SuppressLint("ValidFragment")
public class SecondView extends Fragment {
    List<String> firstDTab = new ArrayList<String>();
    List<String> secondDTab = new ArrayList<String>();
    int nGri,nCol = 0;
    int leftMargin = 10;
    int topMargin = 30;
    int rightMargin = 0;
    int bottomMargin = 0;
    int width = 400;
    int widthS = 100;

    int height = 45;
    int widthP = 150;
    public SecondView(List<String> f, List<String> s, int nGrid, int nColums) {
        firstDTab = f;
        secondDTab = s;
        nGri = nGrid;
        nCol = nColums;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.second_fragment, container, false);
        LinearLayout firstTab = V.findViewById(R.id.linearLayout3);
        LinearLayout secondTab = V.findViewById(R.id.linearLayout4);

        LinearLayout.LayoutParams layoutParams1;
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(1, Color.BLACK);
        firstTab.setBackground(gd);
        secondTab.setBackground(gd);
      //  for (int i = 0; i < nGri; i++) {

        LinearLayout row = new LinearLayout(getActivity());
        LinearLayout rowSecond = new LinearLayout(getActivity());

        //row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        rowSecond.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        for (int j = 0; j < secondDTab.size(); j++) {
            // if (j % 2 != 0) {

            LinearLayout layoutV = (LinearLayout) inflater.inflate(R.layout.button_layout, null, false);
            layoutV.setId(j);
            Button temp = layoutV.findViewById(R.id.button2);
            temp.setText(secondDTab.get(j));
            LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams(600, 100);
            lay.gravity = Gravity.CENTER;
            temp.setLayoutParams(lay);
            //temp.setBackground(gd);
            //layoutV.addView(temp);
            //layoutV.setBackgroundColor(Color.RED);
            lay.setMargins(50, 40, 20, 0);
            row.addView(layoutV);
            row.setOrientation(LinearLayout.VERTICAL);

        }
        secondTab.addView(row);

       // }
            return V;
        }

}
