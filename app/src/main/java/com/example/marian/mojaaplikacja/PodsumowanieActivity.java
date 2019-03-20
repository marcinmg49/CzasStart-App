package com.example.marian.mojaaplikacja;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PodsumowanieActivity extends AppCompatActivity {

    ListView lv;
    double dist;
    double kal;
    List<String> ilosc;
    String id1;

    String data;
    String czas;
    String dystans;
    String kalorie;
    String typ;

    DecimalFormat dd = new DecimalFormat("###.##");





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podsumowanie);

        final TextView tvDystans1 = (TextView) findViewById(R.id.tvDystans1);
        final TextView tvKalorie1 = (TextView) findViewById(R.id.tvKalorie1);
        final TextView tvCzas1 = (TextView) findViewById(R.id.tvCzas1);
        final TextView tvData1 = (TextView) findViewById(R.id.tvData1);
        final TextView tvId = (TextView) findViewById(R.id.tvId);
        final TextView tvTyp1 = (TextView) findViewById(R.id.tvTyp1);
        Button bZapisz = (Button) findViewById(R.id.bZapisz);
        lv = (ListView) findViewById(R.id.lvOdznaki);

        String id  = ((Dane)getApplicationContext()).getId_pobrane();

        tvId.setText(id);
        id1 = tvId.getText().toString();


        final Calendar c = Calendar.getInstance();
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);
        tvData1.setText("Data: " + new StringBuilder().append(yy).append("/").append(mm + 1).append("/").append(dd));
        //data do bazy
        data = yy + "/" + (mm + 1) + "/" + dd;

        Intent intent = getIntent();
        dystans = intent.getStringExtra("dystans");
        double dist1 = intent.getDoubleExtra("dystans1", dist);
        kalorie = intent.getStringExtra("kalorie");
        double kal1 = intent.getDoubleExtra("kalorie1", kal);
        czas = intent.getStringExtra("czas");
        typ = intent.getStringExtra("typ");

        String rekord = intent.getStringExtra("rekord");
        double r = Double.parseDouble(rekord);
        String rekord_kalorie = intent.getStringExtra("rekord_kalorie");
        double rk = Double.parseDouble(rekord_kalorie);


        tvDystans1.setText("Dystans: " + dystans);
        tvKalorie1.setText("Kalorie: " + kalorie);
        tvCzas1.setText("Czas: " + czas);
        tvTyp1.setText("Typ treningu: " + typ);

        ilosc = new ArrayList<String>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ilosc);
        lv.setAdapter(arrayAdapter);



        String rekordodleglosc = "";

        if(dist1 > r){
            rekordodleglosc = String.valueOf(dist1);
            ilosc.add("Rekord odległości! "+ "(" + (String.format("%.2f", dist1)) + " km" + ")");
        }else{
            rekordodleglosc = String.valueOf(dist1);
        }
        final String rekord2 = rekordodleglosc;

        String rekordkalorie = "";

        if(kal1 > rk){
            rekordkalorie = String.valueOf(kal1);
            ilosc.add("Rekord spalonych kalorii! "+ "(" + (String.format("%.2f", kal1)) + " kcal" + ")");
        }else{
            rekordkalorie = String.valueOf(kal1);
        }
        final String rekord3 = rekordkalorie;





            



        bZapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //rezultaty
                final String id_uzytkownik = tvId.getText().toString();
                /*final String data = tvData1.getText().toString();
                final String czas = tvCzas1.getText().toString();
                final String dystans = tvDystans1.getText().toString();
                final String kalorie = tvKalorie1.getText().toString();
                final String typ1 = tvTyp1.getText().toString();*/


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                                Bundle bundle = new Bundle();

                                bundle.putString("id_uzytkownik", id_uzytkownik);
                                bundle.putString("data", data);
                                bundle.putString("czas", czas);
                                bundle.putString("dystans", dystans);
                                bundle.putString("kalorie", kalorie);
                                bundle.putString("typ", typ);




                                CharSequence text = "Trening zapisany";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                                toast.show();




                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                builder.setMessage("Problem z połączeniem")
                                        .setNegativeButton("Spróbuj jeszcza raz", null)
                                        .create()
                                        .show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                DodajWpisTeren dodajwpisteren = new DodajWpisTeren(id_uzytkownik, data, czas, dystans, kalorie, typ, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(dodajwpisteren);


                final String nazwa_uzytkownika = ((Dane)getApplicationContext()).getNazwa_u();
                Response.Listener<String> response = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                                Bundle bundle = new Bundle();


                                bundle.putString("rekord", rekord2);
                                bundle.putString("rekord_kalorie", rekord3);
                                bundle.putString("nazwa_uzytkownika", nazwa_uzytkownika);


                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                builder.setMessage("Problem z połączeniem")
                                        .setNegativeButton("Spróbuj jeszcza raz", null)
                                        .create()
                                        .show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                DodajWpisOdznaki dodajwpisodznaki = new DodajWpisOdznaki(rekord2, rekord3, nazwa_uzytkownika, response);
                RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
                queue1.add(dodajwpisodznaki);



            }
        });






    }



}
