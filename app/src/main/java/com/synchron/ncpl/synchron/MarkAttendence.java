package com.synchron.ncpl.synchron;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MarkAttendence extends AppCompatActivity {

    Button back,home;
    ImageView img;
    String image1,userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendence);
        img = (ImageView)findViewById(R.id.img_code);

        back = (Button)findViewById(R.id.btn_back);
        home = (Button)findViewById(R.id.btn_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent in = getIntent();
        userid = in.getStringExtra("UserId");
        loadUrlData1("http://www.synchron.6elements.net/webservices/phpqrcode/index.php?id=" + userid + "");
        Toast.makeText(getApplicationContext(),userid,Toast.LENGTH_LONG).show();
    }
    void loadUrlData1(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("My Response", response.toString());
                try {
                    JSONArray ja = new JSONArray(response);
                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject ja1 = ja.getJSONObject(i);
                        image1 = ja1.getString("Qrcode");
                        Glide.with(MarkAttendence.this).load(image1).into(img);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("getMethodErrorResponse", "" + error);
            }
        });
        requestQueue.add(stringRequest);
    }
}
