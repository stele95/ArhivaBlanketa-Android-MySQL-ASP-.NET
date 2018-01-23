package com.ab.ssd.ab_arhivablanketa;


import android.content.Intent;
import android.widget.Toast;

import com.ab.ssd.ab_arhivablanketa.Klase.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListeInstance {


     public ArrayList<Rok> listRok;
     public ArrayList<Smer> listSmer;
     public ArrayList<GodinaStudija> listGodSt;
     public ArrayList<Predmet> listPredmet;
     public ArrayList<String> listGodine;

    private static ListeInstance instance;

    public static ListeInstance getInstance(){
        if(instance==null)
        {
            instance=new ListeInstance();

        }

        return  instance;
    }
    //public static void setInstance(ListeInstance k){instance=k;}

    public ListeInstance(){
        listGodine=new ArrayList<>();
        listRok=new ArrayList<>();
        listSmer=new ArrayList<>();
        listGodSt=new ArrayList<>();
        listPredmet= new ArrayList<>();

        listGodine.add("2012");
        listGodine.add("2013");
        listGodine.add("2014");
        listGodine.add("2015");
        listGodine.add("2016");
        listGodine.add("2017");
    }


}
