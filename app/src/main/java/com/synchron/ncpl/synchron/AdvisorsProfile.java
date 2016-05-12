package com.synchron.ncpl.synchron;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class AdvisorsProfile extends AppCompatActivity {
    //String image,name,address,proffesion,eMail,phone,city,country;

    CircleImageView profile;
    TextView phoneNumber,email,advisorFName,advisorLName,advisorPosition,advisoraddress,advisorTopName,advisorTopAddress,office,title;
    Button back;
    String userId;
    String aName,aImage,aPosition,aPlace,aEmail,aPhone,fName,lName,aMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advisors);
        profile = (CircleImageView)findViewById(R.id.img_profile1);
        phoneNumber = (TextView)findViewById(R.id.text_phone);
        email = (TextView)findViewById(R.id.text_mail);
        advisorFName = (TextView)findViewById(R.id.text_fname);
        advisorLName = (TextView)findViewById(R.id.text_lname);
        advisorTopName = (TextView)findViewById(R.id.text_name);
        office = (TextView)findViewById(R.id.text_office);
        advisoraddress = (TextView)findViewById(R.id.text_location);
        advisorPosition = (TextView)findViewById(R.id.text_titile);
        title = (TextView)findViewById(R.id.text_position);
        back = (Button)findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        Toast.makeText(AdvisorsProfile.this, userId, Toast.LENGTH_SHORT).show();
        loadUrl("http://www.synchron.6elements.net/webservices/get_advisor.php?user_id=" + userId + "");

    }

    public void loadUrl(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject ja1 = ja.getJSONObject(i);
                        fName = ja1.getString("FirstName");
                        lName = ja1.getString("LastName");
                        aImage = ja1.getString("Advisor Image");
                        aName = fName+" "+lName;
                        aPosition = ja1.getString("User Position");
                        aPlace = ja1.getString("State");
                        aEmail = ja1.getString("Email Id");
                        aPhone = ja1.getString("Phone Number");
                        aMobile = ja1.getString("Mobile Number");
                       // country = ja1.getString("Country");
                        phoneNumber.setText(aPhone);
                        office.setText(aMobile);
                        email.setText(aEmail);
                        advisorFName.setText(fName);
                        advisorLName.setText(lName);
                        advisorTopName.setText(aName);
                        title.setText(aPosition);
                        advisoraddress.setText(aPlace);
                        advisorPosition.setText(aPosition);
                        Glide.with(AdvisorsProfile.this).load(aImage).into(profile);

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

