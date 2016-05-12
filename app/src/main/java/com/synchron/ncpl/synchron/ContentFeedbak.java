package com.synchron.ncpl.synchron;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class ContentFeedbak extends AppCompatActivity {
    private SeekBar cContent1,cContent2,cContent3,cContent4,cContent5;
    private TextView content1,content2,content3,content4,content5,txt_eventName,txt_eventAddress;
    Button back,submit,home;
    ImageView logo;
    String eventName,eventAdd,eventLogo,eventId;
    String cValue1,cValue2,cValue3,cValue4,cValue5;
    String user_id,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_feedbak);
        content1 = (TextView)findViewById(R.id.text_cContent1);
        content2 = (TextView)findViewById(R.id.text_cContent2);
        content3 = (TextView)findViewById(R.id.text_cContent3);
        content4 = (TextView)findViewById(R.id.text_cContent4);
        content5 = (TextView)findViewById(R.id.text_cContent5);
        txt_eventName = (TextView)findViewById(R.id.text_name);
        submit = (Button)findViewById(R.id.content_feedback);
        home = (Button)findViewById(R.id.btn_fvrt1);
        txt_eventAddress = (TextView)findViewById(R.id.text_place);
        logo = (ImageView)findViewById(R.id.img_profile);
        cContent1 = (SeekBar) findViewById(R.id.seekBar_content1);
        cContent2 = (SeekBar) findViewById(R.id.seekBar_content2);
        cContent3 = (SeekBar) findViewById(R.id.seekBar_content3);
        cContent4 = (SeekBar) findViewById(R.id.seekBar_content4);
        cContent5 = (SeekBar) findViewById(R.id.seekBar_content5);
        back = (Button) findViewById(R.id.btn_back);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EventDetails.class);
                startActivity(intent);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaduserid();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        cContent1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                cValue1 = String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                content1.setText("Reting:" + progress);
            }
        });
        cContent2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                cValue2 = String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                content2.setText("Rating:" + progress);
            }
        });
        cContent3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                cValue3 = String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                content3.setText("Rating:" + progress);
            }
        });
        cContent4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress=0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                cValue4 =String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                content4.setText("Rating:"+progress);
            }
        });
        cContent5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress =progresValue;
                cValue5 =String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                content5.setText("Rating:"+progress);
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
        Glide.with(ContentFeedbak.this).load(eventLogo).into(logo);
    }
    void loaduserid(){
        //Toast.makeText(ContentFeedbak.this, eventId + "\n" + user_id, Toast.LENGTH_SHORT).show();
        userid();

    }

    void userid(){

        url = "http://www.synchron.6elements.net/webservices/speaker_feedback.php?eventid="+eventId+"&userid="+user_id+"&speaker1="+cValue1+"&speaker2="+cValue2+"&speaker3="+cValue3+"&speaker4="+cValue4+"&speaker5="+cValue5+"";

        // JsonArrayRequest jsonRequest = new JsonArrayRequest();
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


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
                ContentFeedbak.this).create();

        // Setting Dialog Title
        //alertDialog.setTitle("Feedback");

        // Setting Dialog Message
        alertDialog.setMessage("Thank you for your feedback");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.signin_checkbox);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                cContent1.setMax(0);
                cContent2.setMax(0);
                cContent3.setMax(0);
                cContent4.setMax(0);
                cContent4.setMax(0);
                content1.setText("");
                content2.setText("");
                content3.setText("");
                content4.setText("");
                content5.setText("");
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }
}
