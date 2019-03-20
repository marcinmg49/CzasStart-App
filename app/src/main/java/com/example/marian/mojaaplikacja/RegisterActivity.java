package com.example.marian.mojaaplikacja;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.*;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Rejestracja...");
        progressDialog.setMessage("Prosze czekać...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);

        final EditText etImie = (EditText) findViewById(R.id.etImie);
        final EditText etNazwa = (EditText) findViewById(R.id.etNazwa);
        final EditText etHaslo = (EditText) findViewById(R.id.etHaslo);
        final EditText etWaga = (EditText) findViewById(R.id.etWaga);
        final Button bRejestracja = (Button) findViewById(R.id.bRejestracja);

        bRejestracja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                final String imie = etImie.getText().toString();
                final String nazwa_uzytkownika = etNazwa.getText().toString();
                final String haslo = etHaslo.getText().toString();
                final String waga = etWaga.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Problem z rejestracją")
                                        .setNegativeButton("Spróbuj jeszcza raz", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Rejestracja rejestracja = new Rejestracja(imie, nazwa_uzytkownika, haslo, waga, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(rejestracja);

                // połączenie do odznak
                final String nazwa2 = nazwa_uzytkownika;
                final String brakujacy = "0.00";
                final String brakujacy1 = "0.00";
                Response.Listener<String> res = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                                Bundle bundle = new Bundle();
                                bundle.putString("nazwa_uzytkownika", nazwa2);
                                bundle.putString("rekord", brakujacy);
                                bundle.putString("rekord_kalorie", brakujacy1);


                                progressDialog.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);



                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Problem z dodaniem brakujących danych")
                                        .setNegativeButton("Spróbuj jeszcza raz", null)
                                        .create()
                                        .show();
                                        progressDialog.dismiss();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                DodajBrakujace dodajBrakujace = new DodajBrakujace(nazwa2, brakujacy, brakujacy1, res);
                RequestQueue queue1 = Volley.newRequestQueue(RegisterActivity.this);
                queue1.add(dodajBrakujace);


            }
        });
    }
}
