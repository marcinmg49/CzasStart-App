package com.example.marian.mojaaplikacja;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DodajWpis extends StringRequest {

    private static final String Wpis_URL = "http://dziennik.comli.com/dodajwpis.php";
    private Map<String, String> params;

    public DodajWpis(String id_uzytkownik, String data_treningu, String opis_treningu, Response.Listener<String> listener){
        super(Method.POST, Wpis_URL, listener, null);
        params = new HashMap<>();
        params.put("id_uzytkownik", id_uzytkownik);
        params.put("data_treningu", data_treningu);
        params.put("opis_treningu", opis_treningu);

    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
