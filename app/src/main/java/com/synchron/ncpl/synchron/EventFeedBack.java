package com.synchron.ncpl.synchron;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EventFeedBack extends AppCompatActivity{
    private SeekBar sEvent,sVenue,sCatering,sSpeaker,sContent;
    private TextView event,venue,catering,speaker,content,speakerFeedback,contentFeedback;
    TextView eventName,eventAddress;
    Button back,home,feedback;
    ImageView logo;
    String eName,eAddress,eLogo,eventId;
    String eventValue,venueValue,cateringValue,speakerValue,contentValue,comment;
    String user_id,url,url1;
    String event1,venue1,catering1,speaker1,content1,comment1;
    EditText edit_comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_feed_back);
        edit_comment= (EditText)findViewById(R.id.edit_comments);
        event = (TextView)findViewById(R.id.text_eEvent);
        venue = (TextView)findViewById(R.id.text_eVenue);
        eventName = (TextView)findViewById(R.id.text_name);
        eventAddress = (TextView)findViewById(R.id.text_position);
        logo = (ImageView)findViewById(R.id.img_profile);
        catering = (TextView)findViewById(R.id.text_eCatering);
        speaker = (TextView)findViewById(R.id.text_eSpeaker);
        content = (TextView)findViewById(R.id.text_eContent);
        speakerFeedback = (TextView)findViewById(R.id.text_seaker_review);
        contentFeedback = (TextView)findViewById(R.id.text_content_review);
        sEvent = (SeekBar) findViewById(R.id.seekBar_event);
        sVenue = (SeekBar) findViewById(R.id.seekBar_venue);
        sCatering = (SeekBar) findViewById(R.id.seekBar_catering);
        sSpeaker = (SeekBar) findViewById(R.id.seekBar_speaker);
        sContent = (SeekBar) findViewById(R.id.seekBar_content);
        feedback=(Button)findViewById(R.id.btn_eventFeedBack);
        Intent intent = getIntent();
        String string=edit_comment.getText().toString();
        eName = intent.getStringExtra("eventName");
        eAddress = intent.getStringExtra("eventAddress");
        eLogo = intent.getStringExtra("eventLogo");
        eventId = intent.getStringExtra("eventid");
        user_id = intent.getStringExtra("userid");
        //Toast.makeText(EventFeedBack.this, eventId+"\n"+ user_id, Toast.LENGTH_SHORT).show();
        loadUrlData();
        eventName.setText(eName);
        eventAddress.setText(eAddress);
        Glide.with(EventFeedBack.this).load(eLogo).into(logo);
        //Toast.makeText(EventFeedBack.this,eName+"\n"+eAddress,Toast.LENGTH_LONG).show();
        home = (Button) findViewById(R.id.btn_fvrt1);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EventDetails.class);
                startActivity(intent);
            }
        });
        back = (Button) findViewById(R.id.btn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        sEvent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            int progress = 0;


            @Override

            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                eventValue = String.valueOf(progress);
                //Toast.makeText(getApplicationContext(), eventValue, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(), "Started tracking seekbar" + progress, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                event.setText("Rating: " + progress);
                //Toast.makeText(getApplicationContext(), progress, Toast.LENGTH_SHORT).show();

            }
        });
        sVenue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress =progresValue;
                venueValue =String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                venue.setText("Rating: " + progress);
            }
        });
        sCatering.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                cateringValue = String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                catering.setText("Rating: " + progress);
            }
        });
        sSpeaker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                speakerValue = String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                speaker.setText("Rating: " + progress);
            }
        });
        sContent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                contentValue = String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                content.setText("Rating: " + progress);
            }
        });

        speakerFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent speaker = new Intent(EventFeedBack.this, SpeakerFeedBack.class);
                speaker.putExtra("Eventname", eName);
                speaker.putExtra("Eventaddress", eAddress);
                speaker.putExtra("Eventlogo", eLogo);
                speaker.putExtra("Eventid", eventId);
                speaker.putExtra("userId", user_id);
                startActivity(speaker);
            }
        });
        contentFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent content = new Intent(EventFeedBack.this,ContentFeedbak.class);
                content.putExtra("Eventname",eName);
                content.putExtra("Eventaddress",eAddress);
                content.putExtra("Eventlogo",eLogo);
                content.putExtra("Eventid", eventId);
                content.putExtra("userId", user_id);
                startActivity(content);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Feedback updated successfully", Toast.LENGTH_LONG).show();
                edit_comment.setText("");

            }

        });


    }
    public void insert(View view){
        comment = edit_comment.getText().toString();
        if (comment.length() > 0) {
            loaduserid();
            AlertDialog alertDialog = new AlertDialog.Builder(
                    EventFeedBack.this).create();

            // Setting Dialog Title
            //alertDialog.setTitle("Feedback");

            // Setting Dialog Message
            alertDialog.setMessage("Thank you for your feedback");

            // Setting Icon to Dialog
            //alertDialog.setIcon(R.drawable.signin_checkbox);

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //onBackPressed();
                    sEvent.setMax(0);
                    sVenue.setMax(0);
                    sCatering.setMax(0);
                    sSpeaker.setMax(0);
                    sContent.setMax(0);
                    event.setText("");
                    venue.setText("");
                    catering.setText("");
                    speaker.setText("");
                    content.setText("");

                }
            });

            // Showing Alert Message
            alertDialog.show();

        }else{
            edit_comment.setError("please enter something");
        }



    }
    void loadUrlData(){


        url1= "http://www.synchron.6elements.net/webservices/get_eventfeedback.php?event_id="+eventId+"&user_id="+user_id+"";
        //url = "http://www.synchron.6elements.net/webservices/Event_Feedback.php?EventId="+eventId+"&UserId="+user_id+"&Event="+eventValue+"&Venu="+venueValue+"&Catering="+cateringValue+"&SpeakerOverall="+speakerValue+"&ContentOverall="+contentValue+"";

        // JsonArrayRequest jsonRequest = new JsonArrayRequest();
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(EventFeedBack.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                Log.e("My Response", response.toString());
                // hideDialog();
                try {
                    JSONArray ja = new JSONArray(response);
                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject ja1 = ja.getJSONObject(i);
                        event1 = ja1.getString("EventRank");
                        venue1 = ja1.getString("VenueRank");
                        catering1 = ja1.getString("CateringRank");
                        speaker1 = ja1.getString("SpeakerOverallRank");
                        content1 = ja1.getString("ContentOverallRank ");
                        comment1 = ja1.getString("Comment ");
                        event.setText("Rating:"+event1);
                        venue.setText("Rating:"+venue1);
                        catering.setText("Rating:"+catering1);
                        speaker.setText("Rating:"+speaker1);
                        content.setText("Rating:"+content1);
                        edit_comment.setText(comment1);
                        int sevent = Integer.parseInt(event1);
                        int svenue = Integer.parseInt(venue1);
                        int scatering = Integer.parseInt(catering1);
                        int sspeaker = Integer.parseInt(speaker1);
                        int scontent = Integer.parseInt(content1);
                        Toast.makeText(EventFeedBack.this, svenue+"\n"+sevent, Toast.LENGTH_SHORT).show();
                        sEvent.setProgress(sevent);
                        sVenue.setProgress(svenue);
                        sCatering.setProgress(scatering);
                        sSpeaker.setProgress(sspeaker);
                        sContent.setProgress(scontent);
                       /* address = ja1.getString("Address ");
                        eMail = ja1.getString("Email Id");
                        phone = ja1.getString("Phone Number");
                        city = ja1.getString("City");
                        country = ja1.getString("Country");*/


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.


                Toast.makeText(EventFeedBack.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        Volley.newRequestQueue(this).add(MyStringRequest);
    }

    void loaduserid(){
        //Toast.makeText(EventFeedBack.this, eventId+"\n"+user_id, Toast.LENGTH_SHORT).show();
        userid();

    }

    void userid(){


      url= "http://www.synchron.6elements.net/webservices/Event_Feedback.php?EventId="+eventId+"&UserId="+user_id+"&Event="+eventValue+"&Venu="+venueValue+"&Catering="+cateringValue+"&SpeakerOverall="+speakerValue+"&ContentOverall="+contentValue+"&comment="+comment+"";
        //url = "http://www.synchron.6elements.net/webservices/Event_Feedback.php?EventId="+eventId+"&UserId="+user_id+"&Event="+eventValue+"&Venu="+venueValue+"&Catering="+cateringValue+"&SpeakerOverall="+speakerValue+"&ContentOverall="+contentValue+"";

        // JsonArrayRequest jsonRequest = new JsonArrayRequest();
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // hideDialog();

                //Toast.makeText(EventFeedBack.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                Log.e("My Response", response.toString());

            }

        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.


                Toast.makeText(EventFeedBack.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        Volley.newRequestQueue(this).add(MyStringRequest);

    }



    }






