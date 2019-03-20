package com.example.marian.mojaaplikacja;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSONZnajomi {
    public static String[] imie;
    public static String[] nazwa_uzytkownika;
    public static String[] ids;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_IMIE = "imie";
    public static final String KEY_NAZWA_UZYTKOWNIKA = "nazwa_uzytkownika";
    public static final String KEY_ID = "id_uzytkownik";
    private JSONArray znajomi = null;
    private String json;

    public ParseJSONZnajomi(String json){
        this.json = json;
    }

    protected void parseJSONZnajomi(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            znajomi = jsonObject.getJSONArray(JSON_ARRAY);


            imie = new String[znajomi.length()];
            nazwa_uzytkownika = new String[znajomi.length()];
            ids = new String[znajomi.length()];

            for(int i=0;i<znajomi.length();i++){
                JSONObject jo = znajomi.getJSONObject(i);
                imie[i] = jo.getString(KEY_IMIE);
                nazwa_uzytkownika[i] = jo.getString(KEY_NAZWA_UZYTKOWNIKA);
                ids[i] = jo.getString(KEY_ID);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
