package com.example.marian.mojaaplikacja;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marcin on 21.10.2017.
 */

public class Usuwanie extends StringRequest {
    private static final String Usn_URL = "http://dziennik.comli.com/usuwanieapk.php";
    private Map<String, String> params;

    public Usuwanie(String id_dane, Response.Listener<String> listener){
        super(Request.Method.POST, Usn_URL, listener, null);
        params = new HashMap<>();
        //params.put("nazwa_uzytkownika", nazwa_uzytkownika);
        params.put("id_dane", id_dane);
        //params.put("id_uzytkownik", id_uzytkownik);
    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
