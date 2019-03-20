package com.example.marian.mojaaplikacja;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends Fragment {

    public LatLng hloc;
    public LatLng loc;

    public Marker mMarker;
    public Polyline line;
    public static FloatingActionButton fabMapa;
    public boolean showingFirst = true;
    private GoogleMap googleMap;
    MapView mapView;
    public static Fragment fragmapa;


    //GPSTracker gps;

    //EditText tvDystans = (EditText) findViewById(R.id.etDist);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_maps, container, false);

        fabMapa = (FloatingActionButton) v.findViewById(R.id.fabMapa);
        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng dom = new LatLng(52.2052683, 18.6332887);
                googleMap.addMarker(new MarkerOptions().position(dom).title("Dom").snippet("Dom twórcy aplikacji :)"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(dom).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });


        fabMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showingFirst == true) {


                    googleMap.setOnMyLocationChangeListener(myLocationChangeListener);
                    showingFirst = false;
                } else {
                    googleMap.clear();

                    hloc = null;
                    showingFirst = true;
                }
            }
        });

            return v;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmapa = this;
    }


    public GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override

        public void onMyLocationChange(Location location) {

            if (hloc == null) {
                loc = new LatLng(location.getLatitude(), location.getLongitude());

                mMarker = googleMap.addMarker(new MarkerOptions().position(loc));
                mMarker.setTitle("Początek śledzenia");
                line = googleMap.addPolyline(new PolylineOptions()
                        .add(loc)
                        .width(3)
                        .color(Color.GREEN));
                if (googleMap != null) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                }
                hloc = loc;

            } else {
                loc = new LatLng(location.getLatitude(), location.getLongitude());

                mMarker = googleMap.addMarker(new MarkerOptions().position(loc));
                mMarker.setVisible(false);
                line = googleMap.addPolyline(new PolylineOptions()
                        .add(hloc)
                        .add(loc)
                        .width(3)
                        .color(Color.RED));

                hloc = loc;
            }

        }
    };




    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
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
