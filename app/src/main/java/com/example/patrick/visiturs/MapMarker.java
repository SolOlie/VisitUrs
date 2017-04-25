package com.example.patrick.visiturs;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapMarker extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Location> locate;
    private DAL dal;
    private String tag = "fejl_40";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_marker);
        dal = new DAL(this);
        locate = dal.selectAll();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Log.d(tag, "Start");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d(tag, "Klar");
        for (Location l:locate)
        {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(l.getLon(),l.getLat())).title(l.getName()));

        }
        if(locate != null && locate.size()> 0)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locate.get(0).getLon(),locate.get(0).getLat()),10f));
    }
}
