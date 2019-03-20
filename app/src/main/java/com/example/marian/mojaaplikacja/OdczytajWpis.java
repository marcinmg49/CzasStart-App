package com.example.marian.mojaaplikacja;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class OdczytajWpis extends StringRequest {
    private static final String Odt_URL = "http://dziennik.comli.com/readtrening.php";
    private Map<String, String> params;

    public OdczytajWpis(String id_uzytkownik, Response.Listener<String> listener){
        super(Request.Method.POST, Odt_URL, listener, null);
        params = new HashMap<>();

        params.put("id_uzytkownik", id_uzytkownik);
    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}

