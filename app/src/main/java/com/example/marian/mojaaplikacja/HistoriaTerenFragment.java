package com.example.marian.mojaaplikacja;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class HistoriaTerenFragment extends Fragment {

    public static final String URL = "http://dziennik.comli.com/readteren.php?id=";
    public static Fragment myF;

    private ListView lvTeren;
    private CustomListTeren clt;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_historia_teren, container, false);

        lvTeren = (ListView) v.findViewById(R.id.lvTeren);

        sendRequest();

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myF = this;
    }

    public void refreshFragment(){
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
    }

    private void  sendRequest(){
        // ID!
        String id  = ((Dane)getActivity().getApplicationContext()).getId_pobrane();
        String url= URL;
        url = url+id;

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        ParseJSONTeren p = new ParseJSONTeren(json);
        p.parseJSONTeren();
        clt = new CustomListTeren(getActivity(), ParseJSONTeren.data, ParseJSONTeren.czas, ParseJSONTeren.dystans, ParseJSONTeren.kalorie, ParseJSONTeren.typ, ParseJSONTeren.ids);

        lvTeren.setAdapter(clt);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Historia treningów");

    }





}
