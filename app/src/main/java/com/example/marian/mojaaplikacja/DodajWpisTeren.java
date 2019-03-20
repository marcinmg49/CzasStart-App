package com.example.marian.mojaaplikacja;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marcin on 12.11.2017.
 */

public class DodajWpisTeren extends StringRequest {

    private static final String Wpis_URL = "http://dziennik.comli.com/dodajwpisteren.php";
    private Map<String, String> params;

    public DodajWpisTeren(String id_uzytkownik, String data, String czas, String dystans, String kalorie, String typ, Response.Listener<String> listener){
        super(Method.POST, Wpis_URL, listener, null);
        params = new HashMap<>();
        params.put("id_uzytkownik", id_uzytkownik);
        params.put("data", data);
        params.put("czas", czas);
        params.put("dystans", dystans);
        params.put("kalorie", kalorie);
        params.put("typ", typ);

    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}