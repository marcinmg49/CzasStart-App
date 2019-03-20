package com.example.marian.mojaaplikacja;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class ZnajomiFragment extends Fragment {


    public static final String URL = "http://dziennik.comli.com/readznajomi.php?id=";
    private ListView lvZnajomi;
    private CustomListZnajomi clz;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_znajomi, container, false);

        lvZnajomi = (ListView) v.findViewById(R.id.lvZnajomi);

        sendRequest();



        return v;
    }

    private void  sendRequest(){
        // ID!
        String id  = ((Dane)getActivity().getApplicationContext()).getId_pobrane();
        String url = URL;
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
        ParseJSONZnajomi z = new ParseJSONZnajomi(json);
        z.parseJSONZnajomi();
        clz = new CustomListZnajomi(getActivity(), ParseJSONZnajomi.imie, ParseJSONZnajomi.nazwa_uzytkownika, ParseJSONZnajomi.ids);
        lvZnajomi.setAdapter(clz);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Społeczność");

    }



}
