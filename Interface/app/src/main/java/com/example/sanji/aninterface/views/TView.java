package com.example.sanji.aninterface.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sanji.aninterface.R;
import com.example.sanji.aninterface.messageFormat.LayoutMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian-polymorse on 3/22/18.
 */

public class TView extends BaseAdapter  {
    private final Context mContext;
    private final List<TextView> textViewList;
    LayoutInflater inflter;
    List<LayoutMessage> tab;
    List<String> idList;

    int widthP = 250;
    // 1
    public TView(Context context, List<TextView> te, List<LayoutMessage> t, List<String> i) {
        this.mContext = context;
        this.textViewList = te;
        inflter = (LayoutInflater.from(mContext));
        tab = t;
        idList = i;
    }

    @Override
    public int getCount() {
        return textViewList.size();
    }

    @Override
    public Object getItem(int i) {
        return textViewList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView( int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.sample_can, null); // inflate the layout
         TextView dummyTextView = (TextView)view.findViewById(R.id.textView);
        dummyTextView.setText(textViewList.get(i).getText().toString());
        dummyTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        dummyTextView.setTextColor(Color.BLACK);

        try {

            String [] digit = textViewList.get(i).getText().toString().split(" ");
            List<String>digitList = new ArrayList<>();
            digitList.add(digit[0]);
            for(LayoutMessage l : tab){
                    if(l.getName()!=null && l.getName().matches(textViewList.get(i).getText().toString()))
                    {
                        double d = l.getDisplay();
                        double mini = l.getMinAcceptable();
                        double maxi =l.getMaxAcceptable();

    System.out.println(textViewList.get(i).getText().toString() + " " + l.getDisplay());
                        if(d<mini || d>maxi){
                            /*if(isNumeric(textViewList.get(i).getText().toString()))
                                dummyTextView = textViewList.get(i);*/
                            dummyTextView.setBackgroundColor(Color.RED);}
                        else if(d>mini || d<maxi){

                            dummyTextView.setBackgroundColor(Color.GREEN);}
                        else{
                            dummyTextView.setBackgroundColor(Color.TRANSPARENT);}
                    }
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return dummyTextView;
    }
    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c) && c != '.') return false;
        }
        return true;
    }
}
