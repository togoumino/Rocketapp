package com.example.sanji.aninterface.fragmentView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sanji.aninterface.rest.Client;
import com.example.sanji.aninterface.R;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian-polymorse on 3/17/18.
 */

@SuppressLint("ValidFragment")
public class ThirdView extends Fragment {
    private Client client = null;
    List<String> firstDTab = new ArrayList<String>();
    List<String> secondDTab = new ArrayList<String>();
    int leftMargin = 10;
    int topMargin = 30;
    int rightMargin = 0;
    int bottomMargin = 0;
    int rownum = 3;
    List<LinearLayout> canTable = new ArrayList<LinearLayout>();
    int width = 200;
    int widthS = 100;
    org.osmdroid.views.MapView map = null;

    int height = 45;
    int widthP = 150;

    public ThirdView(List<String> f) {
        firstDTab = f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.third_fragment, container, false);
        LinearLayout firstTab = V.findViewById(R.id.linearLayout5);

        LinearLayout.LayoutParams layoutParams1;
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(1, Color.BLACK);
        firstTab.setBackground(gd);
      /*  map = (org.osmdroid.views.MapView)V.findViewById(R.id.maposm);
        org.osmdroid.config.Configuration.getInstance().setOsmdroidBasePath(new File(Environment.getExternalStorageDirectory(), "osmdroid"));
        GeoPoint sparepoint = new GeoPoint(32.9401475, -106.9193209);
        GeoPoint motel = new GeoPoint(32.3417429, -106.7628682);
        GeoPoint convention = new GeoPoint(32.2799304, -106.7468314);
        GeoPoint guire = new GeoPoint(46.0035479, -72.7311097);
        map.setTileSource(new XYTileSource("OSMPublicTransport", 0, 12, 256, ".png", new String[] {}));
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        map.setUseDataConnection(false);
        IMapController mapController = map.getController();
        mapController.setZoom(9);
        mapController.setCenter(sparepoint);
        setmapin(sparepoint);
        setmapin(motel);
        setmapin(convention);
        setmapin(guire);*/
        //firstTab.removeAllViews();
       // firstTab.addView(map);
       /* for(int i = 0; i< rownum; i++){

            LinearLayout row = new LinearLayout(getActivity());
            LinearLayout rowSecond = new LinearLayout(getActivity());

            //row.setOrientation(LinearLayout.VERTICAL);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            rowSecond.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            for(int j = 0; j< firstDTab.size();j++){
                if(j%2 != 0){
                    layoutParams1 = new LinearLayout.LayoutParams(width, height);}
                else {
                    layoutParams1 = new LinearLayout.LayoutParams(widthP, height);
                }
                LinearLayout layoutV = (LinearLayout) inflater.inflate(R.layout.sample_can, null, false);
                layoutV.setId(i);
                TextView temp  = new TextView(getActivity());
                if(j%2==0)
                    temp.setText(firstDTab.get(j));
                temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                //temp.setBackground(gd);
                layoutV.addView(temp);
                layoutV.setBackgroundColor(Color.RED);
                layoutParams1.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);
                layoutV.setLayoutParams(layoutParams1);
                row.addView(layoutV);
            }
            firstTab.addView(row);
        }*/
        return V;
    }
    public void setmapin(GeoPoint position){
        Marker startMarker = new Marker(map);
        startMarker.setPosition(position);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(startMarker);
    }
}
