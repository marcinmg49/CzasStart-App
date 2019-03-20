package com.example.marian.mojaaplikacja;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DodajBrakujace extends StringRequest {

    private static final String Wpis_URL = "http://dziennik.comli.com/dodajbrakujace.php";
    private Map<String, String> params;

    public DodajBrakujace(String nazwa_uzytkownika, String rekord, String rekord_kalorie, Response.Listener<String> listener){
        super(Method.POST, Wpis_URL, listener, null);
        params = new HashMap<>();
        params.put("nazwa_uzytkownika", nazwa_uzytkownika);
        params.put("rekord", rekord);
        params.put("rekord_kalorie", rekord_kalorie);

    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
