package com.example.marian.mojaaplikacja;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Aktualizacja_wpis extends StringRequest {

    private static final String Wpis_URL = "http://dziennik.comli.com/aktualizujwpis.php";
    private Map<String, String> params;

    public Aktualizacja_wpis(String opis_treningu, String id_dane, Response.Listener<String> listener){
        super(Method.POST, Wpis_URL, listener, null);
        params = new HashMap<>();
        params.put("opis_treningu", opis_treningu);
        params.put("id_dane", id_dane);



    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
