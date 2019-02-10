package com.example.sanji.aninterface;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Login extends AppCompatActivity {
    Button loginButton;
    EditText username_input;
    EditText password_input;
    TextView text;
    String map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username_input = findViewById(R.id.username_input);
        password_input = findViewById(R.id.password_input);
        username_input.setTextColor(Color.WHITE);
        username_input.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        username_input.setTextSize(22);
        password_input.setTextColor(Color.WHITE);
        text = findViewById(R.id.textView2);
        text.setText("ORONOS");
        text.setTextColor(Color.WHITE);
        text.setTypeface(Typeface.createFromAsset(getAssets(),"font/DAEMONES.TTF"));

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String ipAddress = Login.this.getString(R.string.host);
        final String port = Login.this.getString(R.string.rest_port);
        final String publicKeyURL = "http://" + ipAddress + ":" + port + "/users/key";


        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = username_input.getText().toString();
                final String password = password_input.getText().toString();


                // Request a string response from the provided URL.
                StringRequest getKeyRequest = new StringRequest(Request.Method.GET, publicKeyURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String publicKey) {
                                String encryptedPassowrd = encrypt(password, publicKey);


                                try {
                                    String loginURL = "http://" + ipAddress + ":" + port + "/users/login";
                                    JSONObject jsonBody = new JSONObject();
                                    jsonBody.put("username", username);
                                    jsonBody.put("password", encryptedPassowrd);
                                    final String requestBody = jsonBody.toString();

                                    StringRequest postLoginRequest = new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.i("VOLLEY", response);
                                            Toast.makeText(Login.this, "You have successfully logged in",
                                                    Toast.LENGTH_LONG).show();


                                            final String configURL = "http://" + ipAddress + ":" + port + "/config/basic";
                                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                                    (Request.Method.GET, configURL, null, new Response.Listener<JSONObject>() {

                                                        @Override
                                                        public void onResponse(JSONObject response) {
                                                            try {
                                                                System.out.println("res"+response);
                                                                String layoutName = response.getString("layout").trim().split("\\.")[0];
                                                                int otherPort = response.getInt("otherPort");
                                                                map = response.getString("map");
                                                                mapInfo.getInstance().setMap(map);
                                                                Intent myIntent = new Intent(Login.this, DrawerNavigationActivity.class);
                                                                myIntent.putExtra("username", username);
                                                                myIntent.putExtra("layoutName", layoutName);
                                                                myIntent.putExtra("otherPort", otherPort);
                                                                Login.this.startActivity(myIntent);
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }, new Response.ErrorListener() {

                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            System.err.println("ERROR WHILE RECEIVING CONFIG");
                                                        }
                                                    });

                                            queue.add(jsonObjectRequest);
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.e("VOLLEY", error.toString());
                                            Toast.makeText(Login.this, "Wrong credentials. Please try again",
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
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println("That didn't work");
                        Toast.makeText(Login.this, "Can't reach the server. Please verify the IP address",
                                Toast.LENGTH_LONG).show();
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(getKeyRequest);

            }
        });
    }

    public final static String encrypt(String text, String pkey) {
        try {
            byte[] encryptedBytes = encryptBytes(formatPublicKey(pkey), text.getBytes());
            return byteArrayToHex(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public static byte[] encryptBytes(PublicKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plaintext);
    }

    public static PublicKey formatPublicKey(String pkey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(Base64.decode(pkey, Base64.DEFAULT));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(publicSpec);
    }
}
