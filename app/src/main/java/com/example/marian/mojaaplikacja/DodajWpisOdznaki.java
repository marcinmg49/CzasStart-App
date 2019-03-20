package com.example.marian.mojaaplikacja;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DodajWpisOdznaki extends StringRequest {

    private static final String Wpis_URL = "http://dziennik.comli.com/dodajwpisodznaki.php";
    private Map<String, String> params;

    public DodajWpisOdznaki(String rekord, String rekord_kalorie, String nazwa_uzytkownika, Response.Listener<String> listener){
        super(Method.POST, Wpis_URL, listener, null);
        params = new HashMap<>();
        params.put("rekord", rekord);
        params.put("rekord_kalorie", rekord_kalorie);
        params.put("nazwa_uzytkownika", nazwa_uzytkownika);



    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
