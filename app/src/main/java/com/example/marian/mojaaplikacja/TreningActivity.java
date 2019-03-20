package com.example.marian.mojaaplikacja;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class TreningActivity extends Fragment {


    private TextView tvPredkosc;
    private TextView tvPredkosc1;
    private TextView tvDystans;
    private TextView tvDystans1;

    private TextView tvGps;
    private TextView tvKalorie;
    private TextView tvKalorie1;
    private TextView tvTimer;
    public static FloatingActionButton fab;
    private FloatingActionButton fabStop;
    private RadioGroup radioGroup;
    private RadioButton radioBieganie;
    private RadioButton radioChodzenie;


    private Chronometer chronometer;
    private boolean wlaczony = false;




    protected LocationManager locationManager;
    public static Fragment frag;


    private static double dist;
    private static double predkosc1;
    public static double kalorie;



    DecimalFormat dp = new DecimalFormat("##.##");
    DecimalFormat dd = new DecimalFormat("####.##");


    private Location savedLocation = null;




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_trening, container, false);



        tvPredkosc = (TextView) v.findViewById(R.id.tvPredkosc);
        tvPredkosc1 = (TextView) v.findViewById(R.id.tvPredkosc1);
        tvDystans = (TextView) v.findViewById(R.id.tvDystans);
        tvDystans1 = (TextView) v.findViewById(R.id.tvDystans1);
        tvGps = (TextView) v.findViewById(R.id.tvGps);
        tvKalorie = (TextView) v.findViewById(R.id.tvKalorie);
        tvKalorie1 = (TextView) v.findViewById(R.id.tvKalorie1);
        chronometer = (Chronometer) v.findViewById(R.id.chronometer3);
        radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        radioBieganie = (RadioButton) v.findViewById(R.id.radioBieganie);
        radioChodzenie = (RadioButton) v.findViewById(R.id.radioChodzenie);


        //TabbedFragment t = (TabbedFragment) TabbedFragment.mf;
        //t.getFragmentManager().findFragmentByTag("tabbed");


        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        //fab = (FloatingActionButton) v.findViewById(R.id.fab1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                fabStop.setVisibility(View.VISIBLE);
                wlaczony = true;

                MapsActivity m = (MapsActivity) MapsActivity.fragmapa;
                m.getFragmentManager().findFragmentByTag("mapka");
                m.fabMapa.callOnClick();
                //((Dane)getActivity().getApplicationContext()).setWlacz(wlaczony);


            }
        });

        fabStop = (FloatingActionButton) v.findViewById(R.id.fabStop);

        fabStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            chronometer.stop();

            long czas = SystemClock.elapsedRealtime() - chronometer.getBase();
            long godziny =  (czas/3600000);
            long minuty = (czas - godziny * 3600000) / 60000;
            long sekundy = (czas - godziny * 3600000 - minuty * 60000) / 1000;
            String godziny1 = String.format("%02d", godziny);
            String minuty1 = String.format("%02d", minuty);
            String sekundy1 = String.format("%02d", sekundy);
            String czas1 = godziny1 + ":" + minuty1 + ":" + sekundy1;

                final Intent intent = new Intent(getActivity(), PodsumowanieActivity.class);
                intent.putExtra("dystans", tvDystans1.getText());
                intent.putExtra("kalorie", tvKalorie1.getText());
                intent.putExtra("czas", czas1);
                intent.putExtra("dystans1", dist);
                intent.putExtra("kalorie1", kalorie);

                String typ1 = "";
                if(radioBieganie.isChecked()) {
                    typ1 = "Bieganie";
                    intent.putExtra("typ", typ1);
                }
                if(radioChodzenie.isChecked()){
                    typ1 = "Chodzenie";
                    intent.putExtra("typ", typ1);
                }




                final String nazwa_uzytkownika = ((Dane)getActivity().getApplicationContext()).getNazwa_u();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                final String rekord = jsonResponse.getString("rekord");
                                final String rekord_kalorie = jsonResponse.getString("rekord_kalorie");




                                    intent.putExtra("rekord", rekord);
                                    intent.putExtra("rekord_kalorie", rekord_kalorie);
                                    startActivity(intent);



                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("Problem z odczytem danych z bazy")
                                        .setNegativeButton("Spróbuj jeszcza raz", null)
                                        .create()
                                        .show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Odznaki odznaki = new Odznaki(nazwa_uzytkownika, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(odznaki);
                // usuwa zapamiętaną trase z pamięci
                locationManager.removeUpdates(locationListener);





            }
        });

        tvDystans1.setText("0.00");
        tvPredkosc1.setText("0.00");
        tvKalorie1.setText("0.00");

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            tvGps.setText("GPS");
        else
            tvGps.setText("GPS Wyłączony!");



        return v;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frag = this;
    }

    protected LocationListener locationListener = new LocationListener() {
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }

        public void onLocationChanged(Location location) {

            if(wlaczony == true) {
                showLocation(location);
            }



            if (savedLocation == null)
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

            savedLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);



        }

    };



    public void onStart() {
        super.onStart();
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
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                2000, 1, locationListener);
    }


    public void onStop() {
        locationManager.removeUpdates(locationListener);
        super.onStop();
    }

    private void showLocation(Location location) {

        String waga  = ((Dane)getActivity().getApplicationContext()).getWaga();
        double waga1 = Double.parseDouble(waga);


        if (location != null) {



            if (savedLocation == null || location == null) {



            } else {


                dist += (location.distanceTo(savedLocation)/1000);

                predkosc1 = (double) ((location.getSpeed()*3600)/1000);

                if(radioBieganie.isChecked()) {
                    kalorie = (0.75 * waga1) * dist;
                }
                if(radioChodzenie.isChecked()){
                    kalorie = (0.53 * waga1) * dist;
                }

            }
            //Dodać kilometry
            tvDystans1.setText(dd.format(dist) + " km");

            tvPredkosc1.setText(dp.format(predkosc1) + " km/h");

            tvKalorie1.setText(dd.format(kalorie) + " kcal");

        }
    }







}
