package com.example.marian.mojaaplikacja;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSON {
    public static String[] daty;
    public static String[] opisy;
    public static String[] ids;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_DATA = "data_treningu";
    public static final String KEY_OPIS = "opis_treningu";
    public static final String KEY_ID = "id_dane";
    private JSONArray zadania = null;
    private String json;

    public ParseJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            zadania = jsonObject.getJSONArray(JSON_ARRAY);


            daty = new String[zadania.length()];
            opisy = new String[zadania.length()];
            ids = new String[zadania.length()];

            for(int i=0;i<zadania.length();i++){
                JSONObject jo = zadania.getJSONObject(i);
                daty[i] = jo.getString(KEY_DATA);
                opisy[i] = jo.getString(KEY_OPIS);
                ids[i] = jo.getString(KEY_ID);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
