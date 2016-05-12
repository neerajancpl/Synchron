package com.synchron.ncpl.synchron;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {
    private  ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private long fileSize = 0;
    final int welcum=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 23) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
            setContentView(R.layout.activity_splash);

            Thread welcome = new Thread() {
                int wait = 0;

                @Override
                public void run() {
                    try {
                        super.run();
                        while (wait < welcum) {
                            sleep(100);
                            wait += 100;
                        }
                    } catch (Exception e) {

                    } finally {

                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                }

            };
            welcome.start();

        progressBar = new ProgressDialog(SplashActivity.this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;

        fileSize = 0;
        new Thread(new Runnable() {
            public void run() {
                while (progressBarStatus < 100) {
                    //progressBarStatus = downloadFile();

                    try {
                        Thread.sleep(1000);
                    }

                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }

                if (progressBarStatus >= 100) {
                    try {
                        Thread.sleep(2000);
                    }

                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.dismiss();
                }
            }
        }).start();
    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.my_profile) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
