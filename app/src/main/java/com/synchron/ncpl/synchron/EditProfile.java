package com.synchron.ncpl.synchron;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity
{
    EditText edit_fname,edit_profesion,edit_location,edit_mail,edit_phone,edit_mobile,edit_pass,edit_newPass,edit_reEnterPass;
    String firstName,proffesion,location,eMail,phone,mobile,password,newpassword,reEnterpassword,userId;
    String fname,uProffesion,uLocation,uMail,uPhone,uMobile,uPassword,profile,imgDecodableString;
    Button ok,back;
    CircleImageView profileImage;
    String result;
    ProgressDialog pDialog;
    //int RESULT_IMAGE = 0, RESULT_CROP = 1;
    //Uri outputFileUri;
    int REQUEST_CAMERA =0,SELECT_FILE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        edit_fname = (EditText) findViewById(R.id.edit_fname);
        edit_profesion = (EditText) findViewById(R.id.edit_profile_proffesion);
        edit_location = (EditText) findViewById(R.id.edit_profile_location);
        edit_mail = (EditText) findViewById(R.id.edit_profile_mail);
        edit_phone = (EditText) findViewById(R.id.edit_profil_phone);
        edit_mobile = (EditText) findViewById(R.id.edit_profile_mobile);
        edit_pass = (EditText) findViewById(R.id.edit_profile_password);
        edit_newPass = (EditText) findViewById(R.id.edit_profile_newPass);
        edit_reEnterPass = (EditText) findViewById(R.id.edit_profile_reenter_pass);
        profileImage = (CircleImageView) findViewById(R.id.btn_prifile_pic);
        ok = (Button) findViewById(R.id.btn_save);
        back = (Button) findViewById(R.id.btn_back);

        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");
        fname = intent.getStringExtra("name");
        uProffesion = intent.getStringExtra("position");
        uLocation = intent.getStringExtra("address");
        uMail = intent.getStringExtra("email");
        uPhone = intent.getStringExtra("phone");
        uMobile = intent.getStringExtra("mobile");
        uPassword = intent.getStringExtra("password");
        profile = intent.getStringExtra("profile_img");
        Toast.makeText(EditProfile.this, userId+""+uPassword, Toast.LENGTH_SHORT).show();
        edit_mail.setText(userId + "" + uMail);
        edit_fname.setText(fname);
        edit_profesion.setText(uProffesion);
        edit_location.setText(uLocation);
        edit_mail.setText(uMail);
        edit_phone.setText(uPhone);
        edit_mobile.setText(uMobile);
        edit_pass.setText(uPassword);
        Glide.with(EditProfile.this).load(profile).into(profileImage);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                firstName = edit_fname.getText().toString();
                proffesion = edit_profesion.getText().toString();
                location = edit_location.getText().toString();
                phone = edit_phone.getText().toString();
                mobile = edit_mobile.getText().toString();
                password = edit_pass.getText().toString();
                newpassword = edit_newPass.getText().toString();
                reEnterpassword = edit_reEnterPass.getText().toString();

                //Toast.makeText(getApplicationContext(), userId+"\n"+ firstName + "\n" + proffesion + "\n" + location + "\n" + eMail + "\n" + phone + "\n" + mobile + "\n" + password + "\n" + newpassword + "\n" + reEnterpassword, Toast.LENGTH_LONG).show();
                loadUrl("http://www.synchron.6elements.net/webservices/update_profile.php?UserId=" + userId + "&firstName=" + firstName + "&proffesion=" + proffesion + "&location=" + location + "&phone=" + phone + " &mobile=" + mobile + "&password=" + password + "&newPassword=" + newpassword + "&reEnterPassword=" + reEnterpassword + "&profilepic=" + result + "");
            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image*//*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       Bitmap thumbnail = null;
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                 thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".png");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profileImage.setImageBitmap(thumbnail);

               ByteArrayOutputStream baos=new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG,100, baos);
                byte [] arr=baos.toByteArray();
                result= Base64.encodeToString(arr, Base64.DEFAULT);


            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                thumbnail = BitmapFactory.decodeFile(selectedImagePath, options);
                profileImage.setImageBitmap(thumbnail);



            }
            ByteArrayOutputStream baos=new  ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG,100, baos);
            byte [] arr=baos.toByteArray();
            result= Base64.encodeToString(arr, Base64.DEFAULT);
        }


    }

 public void loadUrl(String url) {
        // JsonArrayRequest jsonRequest = new JsonArrayRequest();
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                Log.e("My Response", response.toString());
            }
        }, new Response.ErrorListener() {
        //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.

            }
        }) {

        };
        Volley.newRequestQueue(this).add(MyStringRequest);

     /**/AlertDialog alertDialog = new AlertDialog.Builder(
             EditProfile.this).create();

     // Setting Dialog Title
     //alertDialog.setTitle("Feedback");

     // Setting Dialog Message
     alertDialog.setMessage("Updated successfully");

     // Setting Icon to Dialog
     //alertDialog.setIcon(R.drawable.signin_checkbox);

     // Setting OK Button
     alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int which)
         {

             edit_fname.setText("");
             edit_profesion.setText("");
             edit_location.setText("");
             edit_mobile.setText("");
             edit_phone.setText("");
             edit_newPass.setText("");
             edit_reEnterPass.setText("");


         }
     });

             // Showing Alert Message
             alertDialog.show();/**/

    }

}
