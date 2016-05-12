package com.synchron.ncpl.synchron;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
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
import java.util.Map;

public class SpeakerFeedBack extends AppCompatActivity {
    private SeekBar sSpeaker1,sSpeaker2,sSpeaker3,sSpeaker4,sSpeaker5;
    private TextView speaker1,speaker2,speaker3,speaker4,speaker5,txt_eventName,txt_eventAddress;
    Button back,submit,home;
    ImageView logo;
    String eventName,eventAdd,eventLogo,eventId;
    String sValue1,sValue2,sValue3,sValue4,sValue5;

    String user_id,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_feed_back);
        speaker1 = (TextView)findViewById(R.id.text_sSpeaker1);
        speaker2 = (TextView)findViewById(R.id.text_sSpeaker2);
        speaker3 = (TextView)findViewById(R.id.text_sSpeaker3);
        speaker4 = (TextView)findViewById(R.id.text_sSpeaker4);
        speaker5 = (TextView)findViewById(R.id.text_sSpeaker5);
        txt_eventName = (TextView)findViewById(R.id.text_name);
        txt_eventAddress = (TextView)findViewById(R.id.text_place);
        logo = (ImageView)findViewById(R.id.img_profile);
        submit = (Button)findViewById(R.id.btn_submit);
        sSpeaker1 = (SeekBar) findViewById(R.id.seekBar_speaker1);
        sSpeaker2 = (SeekBar) findViewById(R.id.seekBar_speaker2);
        sSpeaker3 = (SeekBar) findViewById(R.id.seekBar_speaker3);
        sSpeaker4 = (SeekBar) findViewById(R.id.seekBar_speaker4);
        sSpeaker5 = (SeekBar) findViewById(R.id.seekBar_speaker5);
        home = (Button) findViewById(R.id.btn_fvrt1);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EventDetails.class);
                startActivity(intent);
            }
        });
        back = (Button) findViewById(R.id.btn_back);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaduserid();
                //Toast.makeText(SpeakerFeedBack.this, "working", Toast.LENGTH_SHORT).show();
            }
        });




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        sSpeaker1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                sValue1 = String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                speaker1.setText("Reting:" + progress);
            }
        });
        sSpeaker2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress =0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress =progresValue;
                sValue2 =String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                speaker2.setText("Rating:"+progress);
            }
        });
        sSpeaker3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress= 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress =progresValue;
                sValue3 =String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                speaker3.setText("Rating:"+progress);
            }
        });
        sSpeaker4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress=0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                sValue4 =String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                speaker4.setText("Rating:"+progress);
            }
        });
        sSpeaker5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress =progresValue;
                sValue5 =String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                speaker5.setText("Rating:"+progress);
            }
        });

        Intent speaker1 = getIntent();
        eventName= speaker1.getStringExtra("Eventname");
        eventAdd= speaker1.getStringExtra("Eventaddress");
        eventLogo= speaker1.getStringExtra("Eventlogo");
        eventId = speaker1.getStringExtra("Eventid");
        user_id = speaker1.getStringExtra("userId");

        txt_eventName.setText(eventName);
        txt_eventAddress.setText(eventAdd);
        Glide.with(SpeakerFeedBack.this).load(eventLogo).into(logo);
    }
    void loaduserid(){
        //Toast.makeText(SpeakerFeedBack.this, eventId+"\n"+user_id, Toast.LENGTH_SHORT).show();
        userid();

    }

    void userid(){



        // url = "http://www.synchron.6elements.net/webservices/login_check.php?username=" + mUsername + "&password=" + mPassword + "";
        // url = "http://www.synchron.6elements.net/webservices/speaker_feedback.php?eventid=1&userid=22&speaker1=4&speaker2=5&speaker3=6&speaker4=7&speaker5=8";
        url = "http://www.synchron.6elements.net/webservices/speaker_feedback.php?eventid="+eventId+"&userid="+user_id+"&speaker1="+sValue1+"&speaker2="+sValue2+"&speaker3="+sValue3+"&speaker4="+sValue4+"&speaker5="+sValue5+"";

        // JsonArrayRequest jsonRequest = new JsonArrayRequest();
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // hideDialog();

                Log.e("My Response", response.toString());
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.

            }
        }) {

        };
        Volley.newRequestQueue(this).add(MyStringRequest);
        AlertDialog alertDialog = new AlertDialog.Builder(
                SpeakerFeedBack.this).create();

        // Setting Dialog Title
        //alertDialog.setTitle("Feedback");

        // Setting Dialog Message
        alertDialog.setMessage("Thank you for your feedback");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.signin_checkbox);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                sSpeaker1.setMax(0);
                sSpeaker2.setMax(0);
                sSpeaker3.setMax(0);
                sSpeaker4.setMax(0);
                sSpeaker5.setMax(0);
                speaker1.setText("");
                speaker2.setText("");
                speaker3.setText("");
                speaker4.setText("");
                speaker5.setText("");
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }






}
