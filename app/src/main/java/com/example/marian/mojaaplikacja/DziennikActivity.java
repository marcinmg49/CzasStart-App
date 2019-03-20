package com.example.marian.mojaaplikacja;

import android.app.DatePickerDialog;
import android.app.Dialog;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;




public class DziennikActivity extends Fragment {

    private ImageView btn;
    private TextView data2;


    private EditText etOpisz;
    private TextView tvData;
    private TextView tvId;
    private Button bDodaj;


    DatePickerDialog datePickerDialog;




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.activity_dziennik, container, false);

        etOpisz = (EditText) v.findViewById(R.id.etOpisz);
        tvData = (TextView) v.findViewById(R.id.tvData);
        tvId = (TextView) v.findViewById(R.id.tvId);
        bDodaj = (Button) v.findViewById(R.id.bDodaj);
        btn = (ImageView) v.findViewById(R.id.bData);





        //Intent intent = getIntent();
        //String id_uzytkownik = intent.getStringExtra("id_uzytkownik");

        String id  = ((Dane)getActivity().getApplicationContext()).getId_pobrane();
        tvId.setText(id);


        //tvId.setText(id_uzytkownik);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int rok1 = c.get(Calendar.YEAR);
                int miesiac1 = c.get(Calendar.MONTH);
                int dzien1 = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int rok,
                                                  int miesiac, int dzien) {

                                tvData.setText(rok + "/" + (miesiac+1) + "/" + dzien);

                            }
                        }, rok1, miesiac1, dzien1);
                datePickerDialog.show();
            }
        });



        bDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String id_uzytkownik = tvId.getText().toString();
                final String data_treningu = tvData.getText().toString();
                final String opis_treningu = etOpisz.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                                Bundle bundle = new Bundle();

                                bundle.putString("id_uzytkownik", id_uzytkownik);
                                bundle.putString("data_treningu", data_treningu);
                                bundle.putString("opis_treningu", opis_treningu);

                                tvData.setText("");
                                etOpisz.setText("");

                                Context context = getActivity();
                                CharSequence text = "Trening zapisany";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();


                                //startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

                DodajWpis dodajwpis = new DodajWpis(id_uzytkownik, data_treningu, opis_treningu, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(dodajwpis);

            }
        });

        return v;

    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Dziennik treningowy");

    }













}
