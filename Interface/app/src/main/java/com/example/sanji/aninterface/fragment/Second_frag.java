package com.example.sanji.aninterface.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import com.example.sanji.aninterface.messageFormat.LayoutMessage;
import com.example.sanji.aninterface.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by christian-polymorse on 3/16/18.
 */

@SuppressLint("ValidFragment")
public class Second_frag extends Fragment {
    private Client client =  null;
    private TabHost mTabHost5;
    private LineGraphSeries<DataPoint> series;
    GraphView mgraph;
    private Runnable mTimer1;
    private Runnable mTimer2;
    private LineGraphSeries<DataPoint> mSeries1;
    View V;
    private List<String> tabID = new ArrayList<>();

    private List<TabHost.TabSpec> listId = new ArrayList<TabHost.TabSpec>();
    public Second_frag(Client tab){
        client = tab;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        V = inflater.inflate(R.layout.second_fragment, null, false);
        mTabHost5 = (TabHost)V.findViewById(R.id.tabHost2);
        mgraph = V.findViewById(R.id.graph);


        creat();
        return V;
    }
    public void creat(){
        mTabHost5.setup();
        mTabHost5.setCurrentTab(0);
        for (int i = 0; i < client.listTotalWidget.get(1).size(); i++) {
            listId.add(mTabHost5.newTabSpec(client.listTotalWidget.get(1).get(i)));

        }


        for (int j = 0; j < listId.size(); j++) {
            listId.get(j).setIndicator( listId.get(j).getTag());

            listId.get(j).setContent(new DummyTabContent(getActivity()));
            mTabHost5.setCurrentTab(0);
            mTabHost5.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {
                    //Toast.makeText(getActivity(), tabId, Toast.LENGTH_SHORT).show();
                    tabID.add(tabId);

                }
            });
            Long debut = System.currentTimeMillis();

            final Handler refreshHandler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    TextView temp  = new TextView(getActivity());
                    GradientDrawable gd = new GradientDrawable();
                    LinearLayout.LayoutParams layoutParams1;
                    FragmentManager fragmentManager = getFragmentManager();

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                    FragmentTransaction replace = null;
                    for(int i=0;i<tabID.size();i++) {
                        for (int a = 0; a < listId.size(); a++){
                            if (tabID.get(i).matches(listId.get(a).getTag())) {
                                FrameLayout fre = mTabHost5.getTabContentView();
                                fre.setId(R.id.layout2);
                                mgraph.setVisibility(View.INVISIBLE);
                                fre.removeAllViews();
                                //break;
                            }

                            if (tabID.get(i).matches(listId.get(a).getTag()) && listId.get(a).getTag().matches("Plots") ) {
                                FrameLayout fre = mTabHost5.getTabContentView();
                                fre.setId(R.id.layout2);
                                mgraph.setVisibility(View.VISIBLE);
                                fre.removeAllViews();
                                double x = 0, y = 0, z=0;
                                DataPoint[] dataPoints = new DataPoint[5];
                                try {
                                    for (LayoutMessage c: client.plotValue) {
                                       double f = System.currentTimeMillis()-debut;
                                       System.out.println("LOol: "+f);
                                        if(c.getId().matches("ICM_ACC_X"))
                                            x = c.getDisplay();
                                        if(c.getId().matches("ICM_ACC_Y"))
                                            y = c.getDisplay();
                                        if(c.getId().matches("ICM_ACC_Z"))
                                            z = c.getDisplay();
                                        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                                                new DataPoint(f/1000, x),
                                                new DataPoint(f/1000, x),
                                                new DataPoint(f/1000, x),

                                        });
                                        LineGraphSeries<DataPoint> seriesY = new LineGraphSeries<>(new DataPoint[] {
                                                new DataPoint(f/1000, y),
                                                new DataPoint(f/1000, y),
                                                new DataPoint(f/1000, y),

                                        });
                                        LineGraphSeries<DataPoint> seriesZ = new LineGraphSeries<>(new DataPoint[] {
                                                new DataPoint(f/1000, z),
                                                new DataPoint(f/1000, z),
                                                new DataPoint(f/1000, z),
                                        });
                                        //mgraph.removeAllSeries();
                                        mgraph.addSeries(series);
                                        mgraph.addSeries(seriesY);
                                        mgraph.addSeries(seriesZ);
                                        series.setColor(Color.BLUE);
                                        seriesY.setColor(Color.RED);
                                        seriesZ.setColor(Color.GREEN);

                                /*LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
                                mgraph.addSeries(series);*/

                                        //for(int k=0;k<6;k++){
                                /*for (int m = 0; m < 5; m++){
                                    //String [] rows = result.get(i);
                                    Log.d(TAG, "Output " + x + " " + y);
                                    dataPoints[i] = new DataPoint(x, y);
                                }
                                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);*/
                                        //  mgraph.addSeries(series);
                                        //  }
                                       /* mgraph.removeAllSeries();
                                        mSeries1 = new LineGraphSeries<>(generateData(c));
                                        mgraph.addSeries(mSeries1);*/
                                    }
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }


                    refreshHandler.postDelayed(this,  4000);
                }
            };
            refreshHandler.postDelayed(runnable,  2000);


            mTabHost5.addTab(listId.get(j));
        }
    }
    public int getSize(){
        return  client.TabHName.size();
    }
    public double isNumeric(String str)
    {
        String[] text = new String[2];
        double num = 0;
        text = str.split(" ");
        try {
            num = Double.parseDouble(text[0]);
        } catch (NumberFormatException e) {
           // Log.i("",text+" is not a number");
        }
        return  num;
    }
    Random mRand = new Random();

    private DataPoint[] generateData(LayoutMessage c) {
        int count = 30;
        DataPoint[] values = new DataPoint[count];
        for (int i=0; i<count; i++) {
            double x = i;
            double f = mRand.nextDouble()*0.15+0.3;
            double y = Math.sin(x*f+2) + mRand.nextDouble()*0.3;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }
}
