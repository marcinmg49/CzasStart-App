package com.example.marian.mojaaplikacja;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSONTeren {
    public static String[] data;
    public static String[] czas;
    public static String[] dystans;
    public static String[] kalorie;
    public static String[] typ;
    public static String[] ids;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_DATA = "data";
    public static final String KEY_CZAS = "czas";
    public static final String KEY_DYSTANS = "dystans";
    public static final String KEY_KALORIE = "kalorie";
    public static final String KEY_TYP = "typ";
    public static final String KEY_ID = "id_dane_t";
    private JSONArray treningi = null;
    private String json;

    public ParseJSONTeren(String json){
        this.json = json;
    }

    protected void parseJSONTeren(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            treningi = jsonObject.getJSONArray(JSON_ARRAY);


            data = new String[treningi.length()];
            czas = new String[treningi.length()];
            dystans = new String[treningi.length()];
            kalorie = new String[treningi.length()];
            typ = new String[treningi.length()];
            ids = new String[treningi.length()];

            for(int i=0;i<treningi.length();i++){
                JSONObject jo = treningi.getJSONObject(i);
                data[i] = jo.getString(KEY_DATA);
                czas[i] = jo.getString(KEY_CZAS);
                dystans[i] = jo.getString(KEY_DYSTANS);
                kalorie[i] = jo.getString(KEY_KALORIE);
                typ[i] = jo.getString(KEY_TYP);
                ids[i] = jo.getString(KEY_ID);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
