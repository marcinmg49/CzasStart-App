package com.example.marian.mojaaplikacja;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Odznaki extends StringRequest{
    private static final String Logowanie_URL = "http://dziennik.comli.com/pobierzodznaki.php";
    private Map<String, String> params;

    public Odznaki(String nazwa_uzytkownika, Response.Listener<String> listener){
        super(Request.Method.POST, Logowanie_URL, listener, null);
        params = new HashMap<>();
        params.put("nazwa_uzytkownika", nazwa_uzytkownika);
        params.get("rekord");
        params.get("rekord_kalorie");

    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
