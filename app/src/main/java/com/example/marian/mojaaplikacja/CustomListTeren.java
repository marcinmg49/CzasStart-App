package com.example.marian.mojaaplikacja;

/**
 * Created by Marian on 2017-04-11.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomListTeren extends ArrayAdapter<String> {


    private String[] data;
    private String[] czas;
    private String[] dystans;
    private String[] kalorie;
    private String[] typ;
    private String[] ids;

    private Activity context;





    public CustomListTeren(Activity context, String[] data, String[] czas, String[] dystans, String[] kalorie, String[] typ,  String[] ids) {
        super(context, R.layout.list_row_teren, data);
        this.context = context;

        this.data = data;
        this.czas = czas;
        this.dystans = dystans;
        this.kalorie = kalorie;
        this.typ = typ;
        this.ids = ids;



    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        final LayoutInflater inflater = context.getLayoutInflater();
        final View lvItem = inflater.inflate(R.layout.list_row_teren, null, true);
         TextView tvData = (TextView) lvItem.findViewById(R.id.data1);
         TextView tvCzas = (TextView) lvItem.findViewById(R.id.czas1);
         TextView tvDystans = (TextView) lvItem.findViewById(R.id.dystans1);
         TextView tvKalorie = (TextView) lvItem.findViewById(R.id.kalorie1);
         TextView tvTyp = (TextView) lvItem.findViewById(R.id.typ1);
        final TextView tvIds = (TextView) lvItem.findViewById(R.id.id1);
        ImageView usun = (ImageView) lvItem.findViewById(R.id.usun1);


        tvData.setText("Data: "+ data[position]);
        tvCzas.setText("Czas: "+ czas[position]);
        tvDystans.setText("Dystans: "+ dystans[position]);
        tvKalorie.setText("Spalone kalorie: "+ kalorie[position]);
        tvTyp.setText("Typ treningu: " + typ[position]);
        tvIds.setText(ids[position]);

        usun.setVisibility(View.VISIBLE);


        usun.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id_dane_t = tvIds.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                                Bundle bundle = new Bundle();
                                bundle.putString("id_dane_t", id_dane_t);

                                HistoriaTerenFragment fg = (HistoriaTerenFragment) HistoriaTerenFragment.myF;
                                fg.getFragmentManager().findFragmentByTag("historia");
                                if(fg != null){
                                    fg.refreshFragment();
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

                UsuwanieTeren usuwanieTeren = new UsuwanieTeren(id_dane_t, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(usuwanieTeren);
            }
        });

        return lvItem;
    }
}
