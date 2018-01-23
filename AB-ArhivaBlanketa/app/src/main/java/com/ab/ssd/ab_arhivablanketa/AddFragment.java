package com.ab.ssd.ab_arhivablanketa;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.ab.ssd.ab_arhivablanketa.Klase.*;

import java.util.ArrayList;


public class AddFragment extends Fragment {

    public static final int TAKE_PIC=2;
    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add, container, false);


        Spinner smer=view.findViewById(R.id.spinnerSmer);
        Spinner godinast=view.findViewById(R.id.spinnerGodSt);
        Spinner predmet=view.findViewById(R.id.spinnerPredmet);
        Spinner rok=view.findViewById(R.id.spinnerRok);
        Spinner godina=view.findViewById(R.id.spinnerGodina);

        ArrayList<Smer> smerovi=new ArrayList<>();
        smerovi.add(new Smer(-1,"[Izaberite]",null));
        for (Smer s:ListeInstance.getInstance().listSmer) {
            smerovi.add(s);
        }

        ArrayList<GodinaStudija> godinest=new ArrayList<>();
        godinest.add(new GodinaStudija(-1,"[Izaberite]"));
        for(GodinaStudija g:ListeInstance.getInstance().listGodSt)
        {
            godinest.add(g);
        }

        ArrayList<Predmet> predmeti=new ArrayList<>();
        predmeti.add(new Predmet(-1,"[Izaberite]",null,null));
        for(Predmet p:ListeInstance.getInstance().listPredmet)
        {
            predmeti.add(p);
        }

        ArrayList<Rok> rokovi=new ArrayList<>();
        rokovi.add(new Rok(-1,"[Izaberite]"));
        for(Rok p:ListeInstance.getInstance().listRok)
        {
            rokovi.add(p);
        }

        ArrayList<String> godine=new ArrayList<>();
        godine.add("[Izaberite]");
        for(String p:ListeInstance.getInstance().listGodine)
        {
            godine.add(p);
        }


        ArrayAdapter spinnerSmer=new ArrayAdapter(this.getContext(),android.R.layout.simple_spinner_item,smerovi);
        smer.setAdapter(spinnerSmer);
        smer.setSelection(0);

        ArrayAdapter spinnerGodSt=new ArrayAdapter(this.getContext(),android.R.layout.simple_spinner_item,godinest);
        godinast.setAdapter(spinnerGodSt);
        godinast.setSelection(0);

        ArrayAdapter spinnerPredmet=new ArrayAdapter(this.getContext(),android.R.layout.simple_spinner_item,predmeti);
        predmet.setAdapter(spinnerPredmet);
        predmet.setSelection(0);

        ArrayAdapter spinnerRok=new ArrayAdapter(this.getContext(),android.R.layout.simple_spinner_item,rokovi);
        rok.setAdapter(spinnerRok);
        rok.setSelection(0);

        ArrayAdapter spinnerGod=new ArrayAdapter(this.getContext(),android.R.layout.simple_spinner_item,godine);
        godina.setAdapter(spinnerGod);
        godina.setSelection(0);

        Button dugmeSlikaj=view.findViewById(R.id.btnAddSlikaj);

        dugmeSlikaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                BottomNavActivity activity=(BottomNavActivity) getActivity();
                activity.startSlikaj();
            }
        });

        Button dugmeIzaberi=view.findViewById(R.id.btnAddIzaberi);

        dugmeIzaberi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                BottomNavActivity activity=(BottomNavActivity) getActivity();
                activity.startIzaberi();
            }
        });

        return view;
    }
}
