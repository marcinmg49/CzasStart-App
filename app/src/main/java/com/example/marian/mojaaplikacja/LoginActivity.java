package com.example.marian.mojaaplikacja;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logowanie...");
        progressDialog.setMessage("Prosze czekać...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);


        final EditText etNazwa = (EditText) findViewById(R.id.etNazwa);
        final EditText etHaslo = (EditText) findViewById(R.id.etHaslo);
        final Button bZaloguj = (Button) findViewById(R.id.bZaloguj);
        final TextView zarejestruj = (TextView) findViewById(R.id.tvRejetracja);

        zarejestruj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rejIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(rejIntent);

            }
        });


        bZaloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                final String nazwa_uzytkownika = etNazwa.getText().toString();
                final String haslo = etHaslo.getText().toString();




                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                String imie = jsonResponse.getString("imie");
                                String id_uzytkownik = jsonResponse.getString("id_uzytkownik");
                                ((Dane)getApplicationContext()).setId_pobrane(id_uzytkownik);
                                String waga = jsonResponse.getString("waga");
                                ((Dane)getApplicationContext()).setWaga(waga);
                                String nazwa = jsonResponse.getString("nazwa_uzytkownika");
                                ((Dane)getApplicationContext()).setNazwa_u(nazwa);



                                Intent intent = new Intent(LoginActivity.this, UzytActivity.class);
                                intent.putExtra("id_uzytkownik", id_uzytkownik);
                                intent.putExtra("imie", imie);
                                intent.putExtra("nazwa_uzytkownika", nazwa_uzytkownika);
                                intent.putExtra("haslo", haslo);
                                intent.putExtra("waga", waga);

                                progressDialog.dismiss();
                                LoginActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Problem z logowaniem")
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

                Logowanie logowanie = new Logowanie(nazwa_uzytkownika, haslo, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(logowanie);

            }
        });
    }
}
