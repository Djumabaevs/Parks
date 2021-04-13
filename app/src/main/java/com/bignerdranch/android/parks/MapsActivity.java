package com.bignerdranch.android.parks;
//https://github.com/Djumabaevs/Parks.git

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.bignerdranch.android.parks.data.AsyncResponse;
import com.bignerdranch.android.parks.data.Repository;
import com.bignerdranch.android.parks.model.Park;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemReselectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if(id == R.id.map_nav_button) {
                //show map view
                mMap.clear();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.map, mapFragment)
                        .commit();

            } else if(id == R.id.park_nav_button) {
                selectedFragment = parksFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.map, selectedFragment)
                        .commit();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        Repository.getParks(new AsyncResponse() {
            @Override
            public void processPark(List<Park> parks) {

                for(Park park : parks) {
                    LatLng sydney = new LatLng(Double.parseDouble(park.getLatitude().toString()),
                            Double.parseDouble(park.getLongitude().toString()));
                    mMap.addMarker(new MarkerOptions().position(sydney).title(park.getFullName()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    Log.d("Park", "processPark: " + park.getFullName());
                }
            }
        });

    }
}