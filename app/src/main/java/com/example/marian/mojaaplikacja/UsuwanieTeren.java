package com.example.marian.mojaaplikacja;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



public class UsuwanieTeren extends StringRequest {
    private static final String Del_URL = "http://dziennik.comli.com/usuwanieterenapk.php";
    private Map<String, String> params;

    public UsuwanieTeren(String id_dane_t, Response.Listener<String> listener){
        super(Request.Method.POST, Del_URL, listener, null);
        params = new HashMap<>();
        params.put("id_dane_t", id_dane_t);

    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
