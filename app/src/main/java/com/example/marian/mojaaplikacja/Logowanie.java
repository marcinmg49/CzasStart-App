package com.example.marian.mojaaplikacja;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Logowanie extends StringRequest{
    private static final String Logowanie_URL = "http://dziennik.comli.com/Login.php";
    private Map<String, String> params;

    public Logowanie(String nazwa_uzytkownika, String haslo, Response.Listener<String> listener){
        super(Request.Method.POST, Logowanie_URL, listener, null);
        params = new HashMap<>();
        params.put("nazwa_uzytkownika", nazwa_uzytkownika);
        params.put("haslo", haslo);

    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
