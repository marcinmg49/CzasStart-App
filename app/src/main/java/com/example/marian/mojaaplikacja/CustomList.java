package com.example.marian.mojaaplikacja;

/**
 * Created by Marian on 2017-04-11.
 */

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomList extends ArrayAdapter<String> {


    private String[] daty;
    private String[] opisy;
    private String[] ids;

    private Activity context;

    private CustomList adapter;





    public CustomList(Activity context, String[] daty, String[] opisy,  String[] ids) {
        super(context, R.layout.list_row, daty);
        this.context = context;

        this.daty = daty;
        this.opisy = opisy;
        this.ids = ids;
        this.adapter = this;


    }




    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        final LayoutInflater inflater = context.getLayoutInflater();
        final View listViewItem = inflater.inflate(R.layout.list_row, null, true);
        final TextView textViewData = (TextView) listViewItem.findViewById(R.id.data);
        final TextView textViewOpis = (TextView) listViewItem.findViewById(R.id.opis);
        final TextView textViewIds = (TextView) listViewItem.findViewById(R.id.ids);
        ImageButton usun = (ImageButton) listViewItem.findViewById(R.id.usun1);
        ImageButton edytuj = (ImageButton) listViewItem.findViewById(R.id.edytuj1);

        textViewData.setText(daty[position]);
        textViewOpis.setText(opisy[position]);
        textViewIds.setText(ids[position]);

        usun.setVisibility(View.VISIBLE);


        usun.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id_dane = textViewIds.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                                Bundle bundle = new Bundle();
                                bundle.putString("id_dane", id_dane);

                                HistoriaTreningowActivity f = (HistoriaTreningowActivity) HistoriaTreningowActivity.myFrg;
                                f.getFragmentManager().findFragmentByTag("historia");
                                if(f != null){
                                    f.refreshFragment();
                                }

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                Usuwanie usuwanie = new Usuwanie(id_dane, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(usuwanie);
            }
        });

        edytuj.setOnClickListener(new Button.OnClickListener() {


            @Override
            public void onClick(View v) {

                final String data = textViewData.getText().toString();
                final String opis = textViewOpis.getText().toString();


                final Dialog custom;
                custom = new Dialog(getContext(), R.style.AppTheme);
                custom.setContentView(R.layout.edycja_dialog);
                custom.setCancelable(true);
                TextView tvData1 = (TextView) custom.findViewById(R.id.tvData1);
                final EditText etEdytuj = (EditText) custom.findViewById(R.id.etEdytuj);
                Button bZapisz = (Button) custom.findViewById(R.id.bZapiszE);
                tvData1.setText(data);
                etEdytuj.setText(opis);

                custom.show();

                bZapisz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String id_dane = textViewIds.getText().toString();
                        final String opis_treningu = etEdytuj.getText().toString();

                        Response.Listener<String> response = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if(success){

                                        Bundle bundle = new Bundle();


                                        bundle.putString("opis_treningu", opis_treningu);
                                        bundle.putString("id_dane", id_dane);

                                        HistoriaTreningowActivity f = (HistoriaTreningowActivity) HistoriaTreningowActivity.myFrg;
                                        f.getFragmentManager().findFragmentByTag("historia");
                                        if(f != null){
                                            f.refreshFragment();
                                        }

                                        custom.dismiss();


                                    }else{
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                        Aktualizacja_wpis aktualizacja_wpis = new Aktualizacja_wpis(opis_treningu, id_dane, response);
                        RequestQueue queue1 = Volley.newRequestQueue(getContext());
                        queue1.add(aktualizacja_wpis);



                    }
                });





            }
        });



        return listViewItem;
    }
}
