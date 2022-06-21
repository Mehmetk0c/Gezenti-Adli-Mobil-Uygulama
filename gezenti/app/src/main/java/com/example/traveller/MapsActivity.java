package com.example.traveller;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        bundle=getIntent().getExtras();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(bundle != null){
            double latt=bundle.getDouble("lat");
            double longt=bundle.getDouble("longtt");
            LatLng sydney = new LatLng(latt, longt);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Hedeflenen Konum"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
        }else{
            LatLng sydney = new LatLng(36.803743, 28.233852);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Hedeflenen Konum"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));

        }


        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(36.803743, 28.233852);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));*/
    }
}