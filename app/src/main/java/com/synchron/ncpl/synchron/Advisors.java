package com.synchron.ncpl.synchron;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Advisors extends AppCompatActivity {

    GridView grid;
    ArrayList<String> a1;
    ArrayList<String> a2;
    ArrayList<String> a3;
    ArrayList<String> p1;
    ArrayList<String> p2;
    ArrayList<String> p3;


    StrictMode.ThreadPolicy polocy;
    String image1,name,userId,fname,lname;

    Button back,home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisors);

        grid = (GridView) findViewById(R.id.gridView);
        loadUrlData1("http://www.synchron.6elements.net/webservices/alladvisors.php");
        polocy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(polocy);
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


    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent in1 = new Intent(Advisors.this, AdvisorsProfile.class);
                in1.putExtra("userId", a2.get(position));
                startActivity(in1);

            //Toast.makeText(Advisors.this, "Name is : " + a1.get(position), Toast.LENGTH_LONG).show();
        }
    });
    }

    class ImageAdapter extends BaseAdapter {
        private Context mContext;


        @Override
        public int getCount() {

            return a1.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

              convertView = getLayoutInflater().inflate(R.layout.grid, null);
              ImageView imageView = (ImageView) convertView.findViewById(R.id.image1);
             TextView name1 = (TextView) convertView.findViewById(R.id.text_name);
             name1.setText(a3.get(position));
             Glide.with(Advisors.this).load(a1.get(position)).into(imageView);

            return convertView;
        }

    }


    void loadUrlData1(String url) {
        a1 = new ArrayList<>();
        a2= new ArrayList<>();
        a3= new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("My Response", response.toString());
                try {
                    JSONArray ja = new JSONArray(response);
                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject ja1 = ja.getJSONObject(i);
                        image1 = ja1.getString("Advisor Image");
                        fname = ja1.getString("FirstName");
                        lname = ja1.getString("LastName");
                        name = fname+" "+lname;
                        /*user_position = ja1.getString("User Position");
                        address = ja1.getString("Address ");
                        eMail = ja1.getString("Email Id");
                        phone = ja1.getString("Phone Number");
                        city = ja1.getString("City");
                        country = ja1.getString("Country");*/
                        userId = ja1.getString("UseriId");
                        a1.add(image1);
                        a2.add(userId);
                        a3.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                grid.setAdapter(new ImageAdapter());
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