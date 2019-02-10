package com.example.sanji.aninterface;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanji.aninterface.fragment.First_frag;
import com.example.sanji.aninterface.fragment.Fourth_frag;
import com.example.sanji.aninterface.fragment.Second_frag;
import com.example.sanji.aninterface.fragment.Third_frag;
import com.example.sanji.aninterface.pdfContent.PdfActivity;
import com.example.sanji.aninterface.rest.Client;
import com.example.sanji.aninterface.rest.RestApi;
import com.example.sanji.aninterface.socketManage.SocketModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.disposables.Disposable;

public class DrawerNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView v;
    Bundle bundle = null;
    private PagerAdapter mPagerAdapter;
    String username;
    String layoutName;
    int otherPort;
    Handler handler = new Handler();
    Disposable d;
    private ProgressDialog progress;
    final int INTERVAL_DELAY = 500;
    private RestApi restAPI = new RestApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        Start here

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        layoutName = intent.getStringExtra("layoutName");
        otherPort = intent.getIntExtra("otherPort", 5000);
        // progress = new ProgressDialog(this);
        //10.200.10.147 192.168.0.154
        Client client = new Client(DrawerNavigationActivity.this.getString(R.string.host), 3000, "/config/rockets/" + layoutName);

        try {
            client.execute().get(800, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


        this.setTitle(client.appName + "(#" + client.appId + ")");
        //  if (!MainActivity.this.isFinishing()){
        List fragments = new Vector();
        android.support.v4.app.Fragment fragFirst = new First_frag(client);
        android.support.v4.app.Fragment secondFrag = new Second_frag(client);
        android.support.v4.app.Fragment thirdFrag = new Third_frag(client);
        android.support.v4.app.Fragment fourthFrag = new Fourth_frag(client);
        // Ajout des Fragments dans la liste
        if (client.TabName.size() != 0)
            fragments.add(fragFirst);
        if (client.TabHName.size() != 0)
            fragments.add(secondFrag);
        if (client.thirdTabName.size() != 0)
            fragments.add(thirdFrag);
        if (client.lastTabName.size() != 0)
            fragments.add(fourthFrag);
        // Fragments
        this.mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        ViewPager pager = (ViewPager) super.findViewById(R.id.viewPager);
        pager.setAdapter(this.mPagerAdapter);
        pager.setOffscreenPageLimit(3);
        SocketModule socketModule = new SocketModule(client.dstAddress, otherPort, client);
        socketModule.start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pdf) {
            // Handle the camera action
            Intent pdfIntent = new Intent(DrawerNavigationActivity.this, PdfActivity.class);
            DrawerNavigationActivity.this.startActivity(pdfIntent);
        } else if (id == R.id.nav_theme_settings) {

        } else if (id == R.id.nav_logout) {
            final RequestQueue queue = Volley.newRequestQueue(this);
            try {
                String ipAddress = DrawerNavigationActivity.this.getString(R.string.host);
                String port = DrawerNavigationActivity.this.getString(R.string.rest_port);
                String logoutURL = "http://" + ipAddress + ":" + port + "/users/logout";
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("username", username);
                final String requestBody = jsonBody.toString();

                StringRequest postLoginRequest = new StringRequest(Request.Method.POST, logoutURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
                        Toast.makeText(DrawerNavigationActivity.this, "You have successfully logged out",
                                Toast.LENGTH_LONG).show();
                        Intent myIntent = new Intent(DrawerNavigationActivity.this, Login.class);
                        DrawerNavigationActivity.this.startActivity(myIntent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                        Toast.makeText(DrawerNavigationActivity.this, "Wrong credentials. Please try again",
                                Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            // can get more details such as response.headers
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };

                queue.add(postLoginRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
