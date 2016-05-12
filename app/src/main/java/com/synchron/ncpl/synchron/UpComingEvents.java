package com.synchron.ncpl.synchron;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpComingEvents extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView list;
    ArrayList<String> a1;
    ArrayList<String> a13;
    ArrayList<String> a14;
    ArrayList<String> a15;
    ArrayList<String> a16;
    ArrayList<String> a17;
    URL url;
    StrictMode.ThreadPolicy polocy;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<String> al2;
    String eventId,attendence,attStatus;
    String username,fullname,address,profileImage,userPosition,userId;
    String event_name,event_logo,event_venue,event_venue1,event_venue2,event_time,event_time1,event_time2;
    TextView nav_user,nav_position,nav_Address;
    CircleImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        fullname = intent.getStringExtra("firstName");
        address = intent.getStringExtra("userAddress");
        profileImage = intent.getStringExtra("userImage");
        userPosition = intent.getStringExtra("userPosition");
        userId = intent.getStringExtra("userId");
        //Toast.makeText(UpComingEvents.this, "Un:"+username+"\nFn:"+fullname+"\nAdd:"+address+"\nPImg:"+profileImage+"\nUposi:"+userPosition+"\nuserid:"+userId, Toast.LENGTH_SHORT).show();

        nav_user = (TextView)hView.findViewById(R.id.txt_name);
        nav_position = (TextView)hView.findViewById(R.id.txt_positon);
        nav_Address = (TextView)hView.findViewById(R.id.txt_address);
        imageView=(CircleImageView)hView.findViewById(R.id.circleView);

        nav_user.setText(fullname);
        nav_position.setText(userPosition);
        nav_Address.setText(address);
        Glide.with(UpComingEvents.this).load(profileImage).into(imageView);


        list = (ListView) findViewById(R.id.list_events);
        //this.getListView().setSelector(R.drawable.your_selector);
        polocy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(polocy);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        loadData("http://www.synchron.6elements.net/webservices/allevents.php?userid=" + userId + "");
        //list.setAdapter(new MyAdapter());
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*if(isItemSelectedBefore(""+position)){
                    view.setBackgroundResource(R.drawable.uevents_list_deselected);
                }else{
                    view.setBackgroundResource(R.drawable.uevents_list_selected);
                }*/
                //list.setBackgroundDrawable(getResources().getDrawable(R.drawable.uevents_list_selected));
                Intent in = new Intent(UpComingEvents.this, EventDetails.class);
                in.putExtra("EventId", a15.get(position));
                in.putExtra("EventAttendence", attendence);
                in.putExtra("username", username);
                in.putExtra("fullname", fullname);
                in.putExtra("UserId",userId);
                startActivity(in);
                //Toast.makeText(UpComingEvents.this, "Name is : " + a1.get(position), Toast.LENGTH_LONG).show();
            }

        });
         swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        //swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData("http://www.synchron.6elements.net/webservices/allevents.php?userid=" + userId + "");
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 2000);
            }
        });
        }
    class MyAdapter extends BaseAdapter {
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

            convertView = getLayoutInflater().inflate(R.layout.list, null);
            TextView t1 = (TextView) convertView.findViewById(R.id.txt_event_name);
            TextView t2 = (TextView) convertView.findViewById(R.id.txt_event_address);
            TextView t3 = (TextView) convertView.findViewById(R.id.txt_event_count);
            TextView t4 = (TextView) convertView.findViewById(R.id.text_date);
            t1.setText(a1.get(position));
            t2.setText(a13.get(position));
            t3.setText(a14.get(position));
            t4.setText(a17.get(position));

            ImageView y1 = (ImageView) convertView.findViewById(R.id.event_image);
            ImageView y2 = (ImageView) convertView.findViewById(R.id.imag_attStatus);

            if(a16.get(position).equals("Attending")){
                y2.setBackgroundResource(R.drawable.atd_attend_btn);
            }else if (a16.get(position).equals("NotAttending")){
                y2.setBackgroundResource(R.drawable.atd_notattend_btn);
            }else if (a16.get(position).equals("Tentative")){
                y2.setBackgroundResource(R.drawable.atd_tentative_btn);
            }

            Glide.with(UpComingEvents.this).load(al2.get(position)).into(y1);

            return convertView;
        }

    }

    public void loadData(String url) {
        a1 = new ArrayList<>();
        al2 = new ArrayList<>();
        a13 = new ArrayList<>();
        a14 = new ArrayList<>();
        a15 = new ArrayList<>();
        a16 = new ArrayList<>();
        a17 = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("My Response", response.toString());
                try {
                    JSONArray ja = new JSONArray(response);
                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject ja1 = ja.getJSONObject(i);

                        event_name = ja1.getString("EventName");
                        event_logo = ja1.getString("EventImage");
                        event_venue1 = ja1.getString("EventCity");
                        event_venue2 = ja1.getString("EventCountry");
                        event_venue = event_venue1+","+event_venue2;
                        event_time1 = ja1.getString("EventStartdate");
                        event_time2 = ja1.getString("EventStartTime");
                        event_time = event_time1+","+event_time2;
                        eventId = ja1.getString("EventId");
                        attendence = ja1.getString("EventAttendance");
                        attStatus = ja1.getString("AttendanceStatus");

                        a1.add(event_name);
                        al2.add(event_logo);
                        a13.add(event_venue);
                        a14.add(attendence);
                        a15.add(eventId);
                        a16.add(attStatus);
                        a17.add(event_time);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                list.setAdapter(new MyAdapter());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("getMethodErrorResponse", "" + error);
            }
        });
        requestQueue.add(stringRequest);
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
        getMenuInflater().inflate(R.menu.events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.my_profile1) {
            Intent profile = new Intent(getApplicationContext(), MyProfile.class);
            profile.putExtra("UserId",userId);
            startActivity(profile);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_myProfile) {
            // Handle the camera action
            Intent in1 = new Intent(getApplicationContext(), MyProfile.class);
            in1.putExtra("UserId",userId);
            startActivity(in1);
        } else if (id == R.id.nav_advisors) {
          Intent in = new Intent(getApplicationContext(),Advisors.class);
            startActivity(in);
        } else if (id == R.id.nav_activityFeed) {
            Intent in = new Intent(getApplicationContext(),ChatActivity.class);
            startActivity(in);

        } else if (id == R.id.nav_businessPartners) {
            Intent in2 = new Intent(getApplicationContext(), BusinessPartners.class);
            in2.putExtra("UserId",userId);
            startActivity(in2);
        } else if(id==R.id.nav_teamMembers){
            Intent tm = new Intent(getApplicationContext(),TeamMembers.class);
            startActivity(tm);

        }else if (id == R.id.nav_events) {
            //Intent intent = new Intent(getApplicationContext(),UpComingEvents.class);
           /* swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefresh);
            //swipeRefreshLayout.setEnabled(false);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(true);
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadData("http://www.synchron.6elements.net/webservices/allevents.php?userid=" + userId + "");
                            swipeRefreshLayout.setRefreshing(false);

                        }
                    }, 3000);
                }
            });*/
            //startActivity(intent);
        } else if (id == R.id.nav_markAttendence) {
            Intent intent = new Intent(getApplicationContext(),MarkAttendence.class);
            intent.putExtra("UserId",userId);
            startActivity(intent);
        } else if (id == R.id.nav_logOut) {
            SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }else if(id==R.id.nav_web){
            Intent in =new Intent(getApplicationContext(),Web.class);
            startActivity(in);

        }else if (id==R.id.nav_location){
            Intent in =new Intent(getApplicationContext(),Location.class);
            startActivity(in);
        }else if (id==R.id.nav_call){
            /*Intent in =new Intent(getApplicationContext(),Web.class);
            startActivity(in);*/
            String number = "+61 3 9328 3900";
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+number));
            startActivity(callIntent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

    }
}
