package com.example.marian.mojaaplikacja;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Rejestracja extends StringRequest {

    private static final String Rejestracja_URL = "http://dziennik.comli.com/Register.php";
    private Map<String, String> params;

    public Rejestracja(String imie, String nazwa_uzytkownika, String haslo, String waga, Response.Listener<String> listener){
    super(Method.POST, Rejestracja_URL, listener, null);
        params = new HashMap<>();
        params.put("imie", imie);
        params.put("nazwa_uzytkownika", nazwa_uzytkownika);
        params.put("haslo", haslo);
        params.put("waga", waga);

    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
