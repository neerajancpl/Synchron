package com.synchron.ncpl.synchron;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    //private static final String TAG = RegisterActivity.class.getSimpleName();
    EditText uname, upass;
    ProgressDialog pDialog;
    ImageView picture;
    String url;
    String name, pass, userName, userImage, userAddress, userPosition=null,userid;
    StringRequest stringRequest;
    CheckBox cbSighned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy po = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(po);

        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        uname = (EditText) findViewById(R.id.edit_username);
        upass = (EditText) findViewById(R.id.edit_password);
        uname.setText("ramsarita211@gmail.com");
        upass.setText("sari@123");
        cbSighned = (CheckBox) findViewById(R.id.check_signin);
        checkIsSighnedIn();

    }

    public void login(View view) {
        name = uname.getText().toString();
        pass = upass.getText().toString();
        if (name != null && !name.isEmpty()) {
            if (pass != null && !pass.isEmpty()) {
                userAuthentication(name, pass);
            } else {
                upass.setError("Enter password");
            }
        } else {
            uname.setError("Enter Username");
        }
    }
    public void userAuthentication(final String mUsername, final String mPassword) {
        Log.v("mUsername:mPassword",mUsername+":"+mPassword);
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        url = "http://www.synchron.6elements.net/webservices/login_check.php?username=" + mUsername + "&password=" + mPassword + "";
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                String msg = null;
                try {
                    JSONArray ja = new JSONArray(response);
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(0);
                        msg = jo.get("message").toString();
                    if(msg.equals("Successfully Login")){
                        Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                        userName = jo.get("Firstname").toString();
                        userAddress = jo.get("State").toString();
                        userImage = jo.get("User image").toString();
                        userPosition = jo.get("User position").toString();
                        userid=jo.getString("UserId").toString();
                        Log.v("USER ID : ",""+userid);
                        //Toast.makeText(LoginActivity.this, "USER ID : "+userid, Toast.LENGTH_SHORT).show();


                        if (cbSighned.isChecked())
                        {
                            SharedPreferences sharedPreferences = getSharedPreferences("myshared", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            sharedPreferences.edit();
                            editor.putString("mUsername", mUsername);
                            editor.putString("mPassword ", mPassword);
                            editor.putString("userid ", userid);
                            editor.commit();
                            Log.v("Store", mUsername + ":" + mPassword);
                        }
                        Intent intent = new Intent(LoginActivity.this, UpComingEvents.class);
                        intent.putExtra("username", mUsername);
                        intent.putExtra("firstName", userName);
                        intent.putExtra("userAddress", userAddress);
                        intent.putExtra("userImage", userImage);
                        intent.putExtra("userPosition", userPosition);
                        intent.putExtra("userId", userid);
                        startActivity(intent);
                        uname.setText("");
                        upass.setText("");
                    }else{
                        Toast.makeText(LoginActivity.this, "Please check the Username or Password", Toast.LENGTH_SHORT).show();
                    }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                };
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                Toast.makeText(LoginActivity.this, "Something went wrong Plz try again...!", Toast.LENGTH_SHORT).show();
                uname.setText("");
                upass.setText("");
                requestQueue.add(stringRequest);
            }
        });


        Volley.newRequestQueue(this).add(stringRequest);

    }

    public void checkIsSighnedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("myshared", MODE_PRIVATE);
        String username_shared = sharedPreferences.getString("mUsername", "");
        String password_shared = sharedPreferences.getString("mPassword", "");
        Log.v("checkIsSighnedIn",username_shared+":"+password_shared);
        if (!username_shared.equals("") && !password_shared.equals("")) {
            userAuthentication(username_shared, password_shared);
            finish();
        }
    }

    public void navigateToHome(String username, String password) {
        uname.setText("");
        upass.setText("");
        cbSighned.setChecked(false);
        Intent in = new Intent(getApplicationContext(), UpComingEvents.class);
        in.putExtra("username", username);
        in.putExtra("password", password);

        startActivity(in);
    }
}
