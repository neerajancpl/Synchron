package com.synchron.ncpl.synchron;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
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

import java.io.IOException;
import java.util.ArrayList;

public class EventDetails extends AppCompatActivity implements View.OnClickListener {
    String status ="";
    ProgressDialog pDialog;
    static double longitude, latitude;
    String username, fullname, sposition, userId;
    StrictMode.ThreadPolicy polocy;
    Button location, agenda, speaker, attendency, back, ok,cancel;
    public static FragmentManager fm;
    Button eventFeedBack, rsvp,chat;
    TextView txtEventName, txtEventStreet, txtEventCity, txtEventCountry, txtPhone, txtMail;
    ImageView y1;
    Dialog dialog;
    String event_name, event_logo, event_city, event_address, event_street, event_phone, event_mail, event_agenda, event_startTime, event_country, event_endTime, event_attendence, event_speaker, speaker_desg, about_speaker,event_StartDate,event_EndDate,eventStart,eventEnd;
    RadioButton attending,notAttending,tentative;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < 23) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        polocy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(polocy);
        setContentView(R.layout.activity_event_details);
        location = (Button) findViewById(R.id.btn_map);
        agenda = (Button) findViewById(R.id.btn_agenda);
        speaker = (Button) findViewById(R.id.btn_speaker);
        attendency = (Button) findViewById(R.id.btn_attendency);
        location.setBackgroundResource(R.drawable.eventdetails_tab_location_active);
        agenda.setBackgroundResource(R.drawable.eventdetails_tab_agenda_inactive);
        speaker.setBackgroundResource(R.drawable.eventdetails_tab_speaker_inactive);
        attendency.setBackgroundResource(R.drawable.eventdetails_tab_attendance_inactive);
        location.setOnClickListener(this);
        agenda.setOnClickListener(this);
        speaker.setOnClickListener(this);
        attendency.setOnClickListener(this);

        fm = getFragmentManager();

        txtEventName = (TextView) findViewById(R.id.text_name);
        txtEventStreet = (TextView) findViewById(R.id.txt_street);
        txtEventCity = (TextView) findViewById(R.id.txt_city);
        txtEventCountry = (TextView) findViewById(R.id.text_country);
        txtPhone = (TextView) findViewById(R.id.txt_phone);
        txtMail = (TextView) findViewById(R.id.text_email);
        //txtAgenda = (TextView)findViewById(R.id.textView8);
        y1 = (ImageView) findViewById(R.id.img_city);

        Intent intent = getIntent();
//        final int position = intent.getStringExtra("EventId");
        sposition = intent.getStringExtra("EventId");
        username = intent.getStringExtra("username");
        userId = intent.getStringExtra("UserId");
        //Toast.makeText(EventDetails.this, sposition, Toast.LENGTH_SHORT).show();
        fullname = intent.getStringExtra("fullname");
        String request_url = "http://www.synchron.6elements.net/webservices/getevent_details.php?event_id=" + sposition + "";
        loadData(request_url);

        eventFeedBack = (Button) findViewById(R.id.btn_feedback);

        rsvp = (Button) findViewById(R.id.btn_rsvp);
        eventFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(EventDetails.this, EventFeedBack.class);
                in1.putExtra("eventName", event_name);
                in1.putExtra("eventAddress", event_address);
                in1.putExtra("eventLogo", event_logo);
                in1.putExtra("eventid", sposition);
                in1.putExtra("userid", userId);
                //Toast.makeText(getApplicationContext(),userId, Toast.LENGTH_LONG).show();

                startActivity(in1);

            }
        });

        rsvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 dialog = new Dialog(EventDetails.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.rsvp_dailog);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                //Window window = dialog.getWindow();
                //window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                attending = (RadioButton)dialog. findViewById(R.id.check_attend);
                notAttending = (RadioButton)dialog. findViewById(R.id.check_notAttending);
                tentative = (RadioButton)dialog. findViewById(R.id.check_tentative);
                ok = (Button)dialog. findViewById(R.id.btn_rsvp_ok);
                cancel =(Button)dialog.findViewById(R.id.btn_rsvp_cancel);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (attending.isChecked()) {
                            status="I am Attending";
                            String url = "http://www.synchron.6elements.net/webservices/rsvp_attendance.php?EventId=" + sposition + "&UserEmailId=" + username + "&Status=Attending";

                            // JsonArrayRequest jsonRequest = new JsonArrayRequest();
                            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // hideDialog();
                                    if (pDialog.isShowing()) {
                                        pDialog.dismiss();
                                    }
                                    Log.e("My Response", response.toString());
                                    //img_attendency.setBackgroundResource(R.drawable.uevents_attending_icon);
                                }
                            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //This code is executed if there is an error.
                                    if (pDialog.isShowing()) {
                                        pDialog.dismiss();
                                    }
                                }
                            }) {

                            };
                            Volley.newRequestQueue(EventDetails.this).add(MyStringRequest);

                        } else if (notAttending.isChecked()) {
                            status="I am NotAttending";
                            String url = "http://www.synchron.6elements.net/webservices/rsvp_attendance.php?EventId=" + sposition + "&UserEmailId=" + username + "&Status=NotAttending";
                            // Request a string response

                            // JsonArrayRequest jsonRequest = new JsonArrayRequest();
                            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // hideDialog();
                                    if (pDialog.isShowing()) {
                                        pDialog.dismiss();
                                    }
                                    Log.e("My Response", response.toString());
                                    //img_attendency.setBackgroundResource(R.drawable.uevents_notattending_icon);
                                }
                            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //This code is executed if there is an error.
                                    if (pDialog.isShowing()) {
                                        pDialog.dismiss();
                                    }
                                }
                            }) {

                            };
                            Volley.newRequestQueue(EventDetails.this).add(MyStringRequest);


                        } else if (tentative.isChecked()) {
                            status="I am Tentetive";
                            String url = "http://www.synchron.6elements.net/webservices/rsvp_attendance.php?EventId=" + sposition + "&UserEmailId=" + username + "&Status=Tentative";


                            // JsonArrayRequest jsonRequest = new JsonArrayRequest();
                            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // hideDialog();
                                    if (pDialog.isShowing()) {
                                        pDialog.dismiss();
                                    }
                                    Log.e("My Response", response.toString());
                                    //img_attendency.setBackgroundResource(R.drawable.uevents_tentative_icon);
                                }
                            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //This code is executed if there is an error.
                                    if (pDialog.isShowing()) {
                                        pDialog.dismiss();
                                    }
                                }
                            }) {

                            };

                            // Add the request to the queue
                            Volley.newRequestQueue(EventDetails.this).add(MyStringRequest);
                        }

                        dialog.cancel();
                        pDialog = new ProgressDialog(EventDetails.this);
                        pDialog.setMessage("Loading...");
                        pDialog.setCancelable(false);
                        pDialog.show();
                    }

                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

            }
        });


        back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });
        chat=(Button) findViewById(R.id.btn_fvrt);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent chatintent=new Intent(getApplicationContext(),ChatActivity.class);
                startActivity(chatintent);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_map:
                location.setBackgroundResource(R.drawable.eventdetails_tab_location_active);
                agenda.setBackgroundResource(R.drawable.eventdetails_tab_agenda_inactive);
                speaker.setBackgroundResource(R.drawable.eventdetails_tab_speaker_inactive);
                attendency.setBackgroundResource(R.drawable.eventdetails_tab_attendance_inactive);
                Bundle bundle = new Bundle();
                bundle.putString("event_address", event_city);
                FragmentTransaction ft = fm.beginTransaction();
                LocationFragment lf = new LocationFragment();
                lf.setArguments(bundle);
                ft.replace(R.id.myframe, lf);
                ft.addToBackStack(null);
                //ft.remove(lf);
                ft.commit();
                break;
            case R.id.btn_agenda:
                location.setBackgroundResource(R.drawable.eventdetails_tab_location_inactive);
                agenda.setBackgroundResource(R.drawable.eventdetails_tab_agenda_active);
                speaker.setBackgroundResource(R.drawable.eventdetails_tab_speaker_inactive);
                attendency.setBackgroundResource(R.drawable.eventdetails_tab_attendance_inactive);
                Bundle bundle1 = new Bundle();
                bundle1.putString("event_agenda", event_agenda);
                bundle1.putString("event_startTime", eventStart);
                bundle1.putString("event_endTime", eventEnd);
                FragmentTransaction ft1 = fm.beginTransaction();
                AgendaFragment af = new AgendaFragment();
                af.setArguments(bundle1);
                ft1.replace(R.id.myframe, af);
                ft1.addToBackStack(null);
                //ft1.remove(af);
                ft1.commit();
                break;
            case R.id.btn_speaker:
                location.setBackgroundResource(R.drawable.eventdetails_tab_location_inactive);
                agenda.setBackgroundResource(R.drawable.eventdetails_tab_agenda_inactive);
                speaker.setBackgroundResource(R.drawable.eventdetails_tab_speaker_active);
                attendency.setBackgroundResource(R.drawable.eventdetails_tab_attendance_inactive);
                Bundle bundle2 = new Bundle();
                bundle2.putString("event_speaker", event_speaker);
                bundle2.putString("SpeakerDesg",speaker_desg);
                bundle2.putString("AboutSpeaker",about_speaker);
                FragmentTransaction ft2 = fm.beginTransaction();
               final SpeakerFragment sf = new SpeakerFragment();
                sf.setArguments(bundle2);
                ft2.replace(R.id.myframe, sf);
                ft2.addToBackStack(null);
                //ft2.remove(sf);
                ft2.commit();
                break;
            case R.id.btn_attendency:
                location.setBackgroundResource(R.drawable.eventdetails_tab_location_inactive);
                agenda.setBackgroundResource(R.drawable.eventdetails_tab_agenda_inactive);
                speaker.setBackgroundResource(R.drawable.eventdetails_tab_speaker_inactive);
                attendency.setBackgroundResource(R.drawable.eventdetails_tab_attendance_active);
                Bundle bundle3 = new Bundle();
                bundle3.putString("event_attendence", event_attendence);
                bundle3.putString("Status", status);
                FragmentTransaction ft3 = fm.beginTransaction();
                AttendencyFragment atf = new AttendencyFragment();
                atf.setArguments(bundle3);
                ft3.replace(R.id.myframe, atf);
                ft3.addToBackStack(null);
                ft3.commit();
                break;
            default:
                break;

        }
    }

    public void loadData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject ja1 = ja.getJSONObject(i);

                        event_name = ja1.getString("EventName");
                        event_logo = ja1.getString("EventLogo");
                        event_city = ja1.getString("EventCity");
                        event_address = ja1.getString("EventAddress");
                        event_street = ja1.getString("ContactPerson");
                        event_phone = ja1.getString("Phonenumber");
                        event_mail = ja1.getString("OrgnaiserEmail");
                        event_startTime = ja1.getString("EventStartTime");
                        event_StartDate = ja1.getString("EventStartdate");
                        eventStart = event_StartDate+","+event_startTime;
                        event_endTime = ja1.getString("EventEndTime");
                        event_EndDate=ja1.getString("EventEnddate");
                        eventEnd = event_EndDate+","+event_endTime;
                        event_country = ja1.getString("EventCountry");
                        event_agenda = ja1.getString("EventTopic");
                        event_attendence = ja1.getString("EventAttendance");
                        event_speaker = ja1.getString("SpeakerName");
                        speaker_desg = ja1.getString("SpeakerDesg");
                        about_speaker = ja1.getString("AboutSpeaker");
                        txtEventName.setText(event_name);
                        txtEventStreet.setText(event_street);
                        txtEventCity.setText(event_city);
                        txtEventCountry.setText(event_address);
                        //Toast.makeText(EventDetails.this,event_address,Toast.LENGTH_LONG).show();
                        txtPhone.setText(event_phone);
                        txtMail.setText(event_mail);
                        Glide.with(EventDetails.this).load(event_logo).into(y1);

                        location();

                        Bundle bundle = new Bundle();
                        //bundle.putString("event_address", event_address);
                        FragmentTransaction ft = fm.beginTransaction();
                        LocationFragment lf = new LocationFragment();
                        lf.setArguments(bundle);
                        ft.add(R.id.myframe, lf);
                        ft.addToBackStack(null);
                        ft.commit();
                        fm.executePendingTransactions();

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
    void location(){
        Geocoder coder = new Geocoder(this);
        String address = event_city+","+event_country;

        //Toast.makeText(EventDetails.this,event_address,Toast.LENGTH_LONG).show();
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(address, 50);
            for (Address add : adresses) {

                longitude = add.getLongitude();
                latitude = add.getLatitude();
                //Toast.makeText(getApplicationContext(), longitude + "\n" + latitude, Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

