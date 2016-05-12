package com.synchron.ncpl.synchron;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MyProfile extends AppCompatActivity {
    Button btnEdit,back;
    CircleImageView profile;
    TextView txt_name,txt_position,txt_place;
    TextView edit_email,edit_phone,edit_mobile,edit_password;
    String userid,firstname,lastname,position, place,profile_img,email,phone,mobile,password,country,address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        txt_name = (TextView)findViewById(R.id.text_name);
        txt_position = (TextView)findViewById(R.id.text_position);
        txt_place = (TextView)findViewById(R.id.text_place);
        profile= (CircleImageView)findViewById(R.id.btn_prifile_pic);
        edit_email = (TextView)findViewById(R.id.edit_mail);
        edit_phone = (TextView)findViewById(R.id.edit_phone);
        edit_mobile = (TextView)findViewById(R.id.edit_mobile);
        edit_password = (TextView)findViewById(R.id.edit_password);
        Intent intent = getIntent();
        userid=intent.getStringExtra("UserId");
        //Toast.makeText(MyProfile.this, userid, Toast.LENGTH_SHORT).show();
        loadData("http://www.synchron.6elements.net/webservices/get_userdetails.php?user_id=" + userid + "");

        btnEdit = (Button)findViewById(R.id.btn_editProfile);
        back = (Button)findViewById(R.id.btn_back);


        btnEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent editProfile = new Intent(getApplicationContext(),EditProfile.class);
                editProfile.putExtra("UserId",userid);
                editProfile.putExtra("name",firstname);
                editProfile.putExtra("position",position);
                editProfile.putExtra("address",address);
                editProfile.putExtra("email",email);
                editProfile.putExtra("phone",phone);
                editProfile.putExtra("mobile",mobile);
                editProfile.putExtra("password",password);
                editProfile.putExtra("profile_img",profile_img);
                startActivity(editProfile);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void loadData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("My Response", response.toString());
                //Toast.makeText(MyProfile.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray ja = new JSONArray(response);
                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject ja1 = ja.getJSONObject(i);

                        firstname = ja1.getString("Firstname");
                        lastname = ja1.getString("Lastname");
                        position = ja1.getString("User Position");
                        place = ja1.getString("City");
                        country = ja1.getString("Country");
                        address = place+"|"+country;
                        profile_img = ja1.getString(" Profile Image");
                        email = ja1.getString("Email Id");
                        String email1 ="    "+email;
                        phone = ja1.getString("Phone Number");
                        mobile = ja1.getString("Mobile Number");
                        password = ja1.getString("Password");
                        txt_name.setText(firstname+" "+lastname);
                        txt_position.setText(position);
                        txt_place.setText(place);
                        edit_email.setText(email1);
                        edit_phone.setText(phone);
                        edit_mobile.setText(mobile);
                        edit_password.setText(password);
                        Glide.with(MyProfile.this).load(profile_img).into(profile);
                        //a14.add(ja1.getString("event_speaker").toString());
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



