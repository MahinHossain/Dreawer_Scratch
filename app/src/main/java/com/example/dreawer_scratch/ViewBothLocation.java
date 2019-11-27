package com.example.dreawer_scratch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.example.dreawer_scratch.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ViewBothLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button btnacceptRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_both_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        btnacceptRequest = findViewById(R.id.btnaccept);




        btnacceptRequest.setText("Ok I am accepting " + getIntent().getStringExtra("patUsername") + " Request");
        btnacceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ParseQuery<ParseObject> patRequest = ParseQuery.getQuery("RequestCar");

                patRequest.whereEqualTo("username", getIntent().getStringExtra("patUsername"));

                patRequest.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {


                        if (objects.size() > 0 && e == null) {


                            for (ParseObject DocRequest : objects) {


                                DocRequest.put("RequestedAccepted", true);
                                DocRequest.put("DoctorOfMe", ParseUser.getCurrentUser().getUsername());
                              // DocRequest.put("MyDoctorPhone",ParseUser.getCurrentUser().get("phn")+"");

                                DocRequest.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {


                                            Intent googleMap = new Intent(Intent.ACTION_VIEW,
                                                    Uri.parse("http://maps.google.com/maps?saddr=" +
                                                           getIntent().getDoubleExtra("docLatitude", 0) +
                                                            "," +
                                                           getIntent().getDoubleExtra("docLongitude", 0)  + "&daddr=" +
                                                            getIntent().getDoubleExtra("patLatitude", 0) + "," +
                                                            getIntent().getDoubleExtra("patLongitude", 0)));


                                            startActivity(googleMap);

                                        }
                                    }
                                });
                            }

                        }
                    }
                });

            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        Double docLat = getIntent().getDoubleExtra("docLatitude", 0);
        Double docLon = getIntent().getDoubleExtra("docLongitude", 0);
        //patien lat long
        Double patLat = getIntent().getDoubleExtra("patLatitude", 0);
        Double patLong = getIntent().getDoubleExtra("patLongitude", 0);


        String patusername = getIntent().getStringExtra("patUsername");


        // Add a marker in Sydney and move the camera
        LatLng drLocation = new LatLng(docLat, docLon);


        LatLng patientLocation = new LatLng(patLat, patLong);


        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        Marker docMarker = mMap.addMarker(new MarkerOptions().position(drLocation).
                title("Doctor Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon)));

        Marker patientMarker = mMap.addMarker(new MarkerOptions().
                position(patientLocation).title("Patient Locatiom").icon(BitmapDescriptorFactory.fromResource(R.drawable.person)));

        ArrayList<Marker> mymarker = new ArrayList<>();

        mymarker.add(docMarker);
        mymarker.add(patientMarker);

        for (Marker marker : mymarker) {
            builder.include(marker.getPosition());
        }

        LatLngBounds bounds = builder.build();


        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 0);
        mMap.animateCamera(cameraUpdate);


    }
}
