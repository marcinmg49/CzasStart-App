package com.example.marian.mojaaplikacja;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Marian on 2017-04-12.
 */

public class Dane extends Application {
    private String id_pobrane;
    private String nazwa_u;
    private String waga;
    private double szerokosc;
    private double dlugosc;
    private boolean wlacz;



    public String getId_pobrane(){
        return id_pobrane;
    }

    public void setId_pobrane(String id_pobrane){
        this.id_pobrane = id_pobrane;
    }

    public String getNazwa_u(){
        return nazwa_u;
    }

    public void setNazwa_u(String nazwa_u){
        this.nazwa_u = nazwa_u;
    }

    public String getWaga(){
        return waga;
    }

    public void setWaga(String waga){
        this.waga = waga;
    }


    public double getSzerokosc(){ return szerokosc;}

    public void setSzerokosc(double szerokosc){
        this.szerokosc = szerokosc;
    }

    public double getDlugosc(){ return dlugosc;}

    public void setDlugosc(double dlugosc){
        this.dlugosc = dlugosc;
    }

    public boolean getWlacz(){ return wlacz;}

    public void setWlacz(boolean wlacz){
        this.wlacz = wlacz;
    }



}
