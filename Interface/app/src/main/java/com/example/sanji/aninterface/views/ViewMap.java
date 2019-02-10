package com.example.sanji.aninterface.views;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.location.LocationManager;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import com.example.sanji.aninterface.R;
import com.example.sanji.aninterface.RocketInfo;
import com.example.sanji.aninterface.mapInfo;

import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.util.AndroidUtil;
import org.mapsforge.map.datastore.MapDataStore;
import org.mapsforge.map.layer.Layer;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.model.MapViewPosition;
import org.mapsforge.map.reader.MapFile;
import org.mapsforge.map.rendertheme.InternalRenderTheme;
import org.mapsforge.map.util.MapViewProjection;
import org.mapsforge.map.view.MapView;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.Marker;

import java.io.File;
import java.util.Map;

@SuppressLint("ValidFragment")
public class ViewMap extends Fragment {
    private static String MAPFILE = "quebec.map";
    private org.mapsforge.map.android.view.MapView mapView;
    private TileCache tileCache;
    private TileRendererLayer tileRendererLayer;
    private MapViewPosition mapViewPosition;
    private IMapController mapController;
    private LocationManager locationManager;
    private LatLong center;
    private LatLong rocket;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    View V;
    public ViewMap() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        V = inflater.inflate(R.layout.map, null, false);;
        mapView = (org.mapsforge.map.android.view.MapView)V.findViewById(R.id.mapforge);
        AndroidGraphicFactory.createInstance(getActivity().getApplication());


        // this.mapView = (org.mapsforge.map.android.view.MapView) findViewById(R.id.mapforge);



        mapView.setClickable(true);
        mapView.getMapScaleBar().setVisible(true);
        mapView.setBuiltInZoomControls(true);
        mapView.getMapZoomControls().setZoomLevelMin((byte) 10);
        mapView.getMapZoomControls().setZoomLevelMax((byte) 20);

        // create a tile cache of suitable size
        this.tileCache = AndroidUtil.createTileCache(this.getContext(), "mapcache", mapView.getModel().displayModel.getTileSize(), 1f, this.mapView.getModel().frameBufferModel.getOverdrawFactor());

        GeoPoint sparepoint = new GeoPoint(32.9401475, -106.9193209);
        GeoPoint motel = new GeoPoint(32.3417429, -106.7628682);
        GeoPoint convention = new GeoPoint(32.2799304, -106.7468314);
        GeoPoint guire = new GeoPoint(46.0035479, -72.7311097);
        getCard();
        return V;
    }
   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidGraphicFactory.createInstance(getActivity().getApplication());


       // this.mapView = (org.mapsforge.map.android.view.MapView) findViewById(R.id.mapforge);


        mapView.setClickable(true);
        mapView.getMapScaleBar().setVisible(true);
        mapView.setBuiltInZoomControls(true);
        mapView.getMapZoomControls().setZoomLevelMin((byte) 10);
        mapView.getMapZoomControls().setZoomLevelMax((byte) 20);

        // create a tile cache of suitable size
        this.tileCache = AndroidUtil.createTileCache(this.getContext(), "mapcache", mapView.getModel().displayModel.getTileSize(), 1f, this.mapView.getModel().frameBufferModel.getOverdrawFactor());

        GeoPoint sparepoint = new GeoPoint(32.9401475, -106.9193209);
        GeoPoint motel = new GeoPoint(32.3417429, -106.7628682);
        GeoPoint convention = new GeoPoint(32.2799304, -106.7468314);
        GeoPoint guire = new GeoPoint(46.0035479, -72.7311097);
    }*/

    @Override
    public void onStart() {
        super.onStart();
       /* rocket = new LatLong(RocketInfo.getInstance().getLat(),RocketInfo.getInstance().getLon());*/
      //  System.out.println(RocketInfo.getInstance().getLon());
        mapView.getModel().mapViewPosition.setCenter(center);
        mapView.getModel().mapViewPosition.setZoomLevel((byte) 12);
        MapDataStore mapDataStore;
        // tile renderer layer using internal render theme
        int permission = ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don’t have permission so prompt the user
            ActivityCompat.requestPermissions(this.getActivity(), PERMISSIONS_STORAGE, 1

            );
            mapDataStore = new MapFile(new File(Environment.getExternalStorageDirectory(), MAPFILE));
        } else {
            mapDataStore = new MapFile(new File(Environment.getExternalStorageDirectory(), MAPFILE));
        }
        mapViewPosition = mapView.getModel().mapViewPosition;
        tileRendererLayer = new TileRendererLayer(tileCache, mapDataStore, mapViewPosition, AndroidGraphicFactory.INSTANCE);

        tileRendererLayer.setXmlRenderTheme(InternalRenderTheme.OSMARENDER);
        mapView.getLayerManager().getLayers().add(tileRendererLayer);
        LatLong position = center;
        Drawable drawable = getActivity().getApplicationContext().getResources().getDrawable(R.drawable.person);
        Bitmap bitmap = AndroidGraphicFactory.convertToBitmap(drawable);
        bitmap.incrementRefCount();
        org.mapsforge.map.layer.overlay.Marker test = new org.mapsforge.map.layer.overlay.Marker(position, bitmap, 0, -bitmap.getHeight() / 2);
        this.mapView.getLayerManager().getLayers().add(test);
       // LatLong position2 = rocket;
        /*Drawable drawable2 = getActivity().getApplicationContext().getResources().getDrawable(R.drawable.direction_arrow);
        Bitmap bitmap2 = AndroidGraphicFactory.convertToBitmap(drawable2);
        bitmap.incrementRefCount();
        org.mapsforge.map.layer.overlay.Marker test2 = new org.mapsforge.map.layer.overlay.Marker(position2, bitmap2, 0, -bitmap2.getHeight() / 2);
        this.mapView.getLayerManager().getLayers().add(test2);*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.destroy();
    }
    public void getCard(){
        switch ( mapInfo.getInstance().getMap()){
            case "spaceport_america":
                MAPFILE = "new-mexico.map";
                center = new LatLong(32.9401475, -106.9193209);
                break;
            case "motel_6":
                MAPFILE = "new-mexico.map";
                center = new LatLong(32.3417429, -106.7628682);
                break;
            case "convention_center":
                MAPFILE = "new-mexico.map";
                center = new LatLong(32.2799304, -106.7468314);
                break;
            case "st-pie_de_guire":
                MAPFILE = "quebec.map";
                center = new LatLong(46.0035479, -72.7311097);
                break;
        }

    }
}
