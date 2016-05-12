package com.synchron.ncpl.synchron;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;

public class Location extends AppCompatActivity {
    static double longitude, latitude;
    MapView mapView;
    GoogleMap map;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        location();
        mapView = (MapView)findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        //map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(Location.this);

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Location.latitude, Location.longitude), 10);
        Marker marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(Location.latitude, Location.longitude))
                .title(address));
        map.animateCamera(cameraUpdate);
    }
    void location(){
        Geocoder coder = new Geocoder(this);
        String address = "65 Palmerston Cres, South Melbourne VIC 3205, Australia";

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
    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
