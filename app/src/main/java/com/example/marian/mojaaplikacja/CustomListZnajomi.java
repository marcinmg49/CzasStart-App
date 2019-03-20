package com.example.marian.mojaaplikacja;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomListZnajomi extends ArrayAdapter<String> {


    private String[] imie;
    private String[] nazwa_uzytkownika;
    private String[] ids;






    private Activity context;


    public CustomListZnajomi(Activity context, String[] imie, String[] nazwa_uzytkownika,  String[] ids) {
        super(context, R.layout.list_row_znajomi, imie);
        this.context = context;

        this.imie = imie;
        this.nazwa_uzytkownika = nazwa_uzytkownika;
        this.ids = ids;



    }




    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        final LayoutInflater inflater = context.getLayoutInflater();
        final View listViewItem = inflater.inflate(R.layout.list_row_znajomi, null, true);
        TextView tvImie = (TextView) listViewItem.findViewById(R.id.tvImie);
        final TextView tvNazwa = (TextView) listViewItem.findViewById(R.id.tvNazwa);
        final TextView  tvId = (TextView) listViewItem.findViewById(R.id.tvId);
        ImageButton ibOdznaki = (ImageButton) listViewItem.findViewById(R.id.ibOdznaki);


        tvImie.setText("Imie: " + imie[position]);
        tvNazwa.setText("Nazwa użytkownika: " + nazwa_uzytkownika[position]);
        tvId.setText(ids[position]);





        ibOdznaki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //final String nazwa = tvNazwa.getText().toString();
                final String nazwa = nazwa_uzytkownika[position];





                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                String rekord = jsonResponse.getString("rekord");
                                String rekord_kalorie = jsonResponse.getString("rekord_kalorie");

                                double rd = Double.parseDouble(rekord);
                                double rk = Double.parseDouble(rekord_kalorie);

                                final Dialog customD;
                                customD = new Dialog(getContext(), R.style.AppTheme);
                                customD.setContentView(R.layout.znajomi_dialog);
                                customD.setCancelable(true);
                                TextView nazwau = (TextView) customD.findViewById(R.id.tvNazwau);
                                TextView rekord1 = (TextView) customD.findViewById(R.id.tvRekord1);
                                TextView rekord2 = (TextView) customD.findViewById(R.id.tvRekord2);
                                Button bWroc = (Button) customD.findViewById(R.id.bWroc);

                                //nazwau.setText(tvNazwa.getText().toString());
                                nazwau.setText(nazwa);
                                rekord1.setText("Najlepsza odległość! " + "(" + String.format("%.2f", rd) + " km" + ")");
                                rekord2.setText("Najwięcej spalonych kalorii! " + "(" + String.format("%.2f", rk) + " kcal"+ ")");

                                customD.show();

                                bWroc.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        customD.dismiss();

                                    }
                                });







                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("Problem z połączeniem")
                                        .setNegativeButton("Spróbuj jeszcze raz", null)
                                        .create()
                                        .show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Odznaki odznaki = new Odznaki(nazwa, responseListener);
                final RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(odznaki);






                /*
                bWroc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customD.dismiss();

                    }
                });
*/



            }
        });



        return listViewItem;
    }
}
