package com.ab.ssd.ab_arhivablanketa;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ab.ssd.ab_arhivablanketa.Klase.Blanket;
import com.ab.ssd.ab_arhivablanketa.Klase.Fakultet;
import com.ab.ssd.ab_arhivablanketa.Klase.GodinaStudija;
import com.ab.ssd.ab_arhivablanketa.Klase.Korisnik;
import com.ab.ssd.ab_arhivablanketa.Klase.Predmet;
import com.ab.ssd.ab_arhivablanketa.Klase.Rok;
import com.ab.ssd.ab_arhivablanketa.Klase.Smer;
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
import java.util.List;


public class SearchFragment extends Fragment {
    View main;
    ArrayList<Blanket> blankets=new ArrayList<>();
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search, container, false);

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

        Button btnsearch= view.findViewById(R.id.btnSearch);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                search();
            }
        });

        main=view;
        return view;
    }

    void search()
    {
        final BottomNavActivity activity = (BottomNavActivity) getActivity();
        activity.setButton(false);

        Toast.makeText(SearchFragment.this.getContext(), "Trazim...", Toast.LENGTH_SHORT).show();

        final Button btnsearch= main.findViewById(R.id.btnSearch);
        btnsearch.setEnabled(false);

        final Spinner spinsmer=main.findViewById(R.id.spinnerSmer);
        final Spinner spingodinast=main.findViewById(R.id.spinnerGodSt);
        final Spinner spinpredmet=main.findViewById(R.id.spinnerPredmet);
        final Spinner spinrok=main.findViewById(R.id.spinnerRok);
        final Spinner spingodina=main.findViewById(R.id.spinnerGodina);
        final CheckBox cbpismeni=main.findViewById(R.id.cbPismeni);
        final CheckBox cbusmeni=main.findViewById(R.id.cbUsmeni);

        spinsmer.setEnabled(false);
        spingodinast.setEnabled(false);
        spinpredmet.setEnabled(false);
        spinrok.setEnabled(false);
        spingodina.setEnabled(false);

        cbpismeni.setEnabled(false);
        cbusmeni.setEnabled(false);


        final Predmet predmet=(Predmet) spinpredmet.getSelectedItem();
        final Rok rok=(Rok) spinrok.getSelectedItem();
        final String godina=(String) spingodina.getSelectedItem();


        String url="http://160.99.38.140:2666/api/Blanket";
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                JSONObject obj = response.getJSONObject(i);
                                Blanket blanket = new Blanket(obj.getInt("IdBlanket"),
                                        new Rok(obj.getJSONObject("Rok").getInt("IdRok"),obj.getJSONObject("Rok").getString("Naziv")),
                                        obj.getInt("Godina"),
                                        new Predmet(obj.getJSONObject("Predmet").getInt("IdPredmet"),obj.getJSONObject("Predmet").getString("Naziv"),
                                                new Smer(obj.getJSONObject("Predmet").getJSONObject("Smer").getInt("IdSmer"),obj.getJSONObject("Predmet").getJSONObject("Smer").getString("Naziv"),
                                                        new Fakultet(obj.getJSONObject("Predmet").getJSONObject("Smer").getJSONObject("Fakultet").getInt("IdFakultet"),obj.getJSONObject("Predmet").getJSONObject("Smer").getJSONObject("Fakultet").getString("Naziv"),obj.getJSONObject("Predmet").getJSONObject("Smer").getJSONObject("Fakultet").getString("Grad"))),
                                                new GodinaStudija(obj.getJSONObject("Predmet").getJSONObject("Godina").getInt("IdGodina"),obj.getJSONObject("Predmet").getJSONObject("Godina").getString("Godina"))),
                                        obj.getString("Datum"),
                                        new Korisnik(obj.getJSONObject("Dodao").getInt("IdKorisnik"),obj.getJSONObject("Dodao").getString("Ime"),obj.getJSONObject("Dodao").getString("Prezime"),obj.getJSONObject("Dodao").getInt("BrIndeksa"),obj.getJSONObject("Dodao").getString("Username"),obj.getJSONObject("Dodao").getString("Email"),obj.getJSONObject("Dodao").getString("Password"),
                                                new Fakultet(obj.getJSONObject("Dodao").getJSONObject("Fakultet").getInt("IdFakultet"),obj.getJSONObject("Dodao").getJSONObject("Fakultet").getString("Naziv"),obj.getJSONObject("Dodao").getJSONObject("Fakultet").getString("Grad")),obj.getJSONObject("Dodao").getBoolean("Admin")),
                                        new Korisnik(obj.getJSONObject("Odobrio").getInt("IdKorisnik"),obj.getJSONObject("Odobrio").getString("Ime"),obj.getJSONObject("Odobrio").getString("Prezime"),obj.getJSONObject("Odobrio").getInt("BrIndeksa"),obj.getJSONObject("Odobrio").getString("Username"),obj.getJSONObject("Odobrio").getString("Email"),obj.getJSONObject("Odobrio").getString("Password"),
                                                new Fakultet(obj.getJSONObject("Odobrio").getJSONObject("Fakultet").getInt("IdFakultet"),obj.getJSONObject("Odobrio").getJSONObject("Fakultet").getString("Naziv"),obj.getJSONObject("Odobrio").getJSONObject("Fakultet").getString("Grad")),obj.getJSONObject("Odobrio").getBoolean("Admin")),
                                        obj.getBoolean("Odobren"),
                                        obj.getBoolean("Pismeni"),
                                        obj.getBoolean("Usmeni"),
                                        null,
                                        null);
                                //blanket.slika=blanket.getBitmapFromString(obj.getString("ImageInBytes"));
                                blanket.thumbnail=blanket.getBitmapFromString(obj.getString("ThumbnailInBytes"));
                                blankets.add(blanket);

                            } catch (Exception e) {
                                Toast.makeText(SearchFragment.this.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }

                        ArrayList<Blanket> array=new ArrayList<>();

                        IZABRANO izabrano=question(predmet.ID,rok.ID,godina);

                        switch (izabrano)
                        {
                            case PREDMET:
                                for(Blanket b:blankets)
                                {
                                    if(b.predmet.ID==predmet.ID)
                                        array.add(b);
                                }
                                break;
                            case ROK:
                                for(Blanket b:blankets)
                                {
                                    if(b.rok.ID==rok.ID)
                                        array.add(b);
                                }
                                break;
                            case GODINA:
                                for(Blanket b:blankets)
                                {
                                    if(b.godina==Integer.parseInt(godina))
                                        array.add(b);
                                }
                                break;
                            case PiR:
                                for(Blanket b:blankets)
                                {
                                    if(b.predmet.ID==predmet.ID && b.rok.ID==rok.ID)
                                        array.add(b);
                                }
                                break;
                            case PiG:
                                for(Blanket b:blankets )
                                {
                                    if(b.predmet.ID==predmet.ID && b.godina==Integer.parseInt(godina))
                                        array.add(b);
                                }
                                break;
                            case RiG:
                                for(Blanket b:blankets)
                                {
                                    if(b.rok.ID==rok.ID && b.godina==Integer.parseInt(godina))
                                        array.add(b);
                                }
                                break;
                            case PiRiG:
                                for(Blanket b:blankets)
                                {
                                    if(b.predmet.ID==predmet.ID && b.rok.ID==rok.ID && b.godina==Integer.parseInt(godina))
                                        array.add(b);
                                }
                                break;
                            case NISTA:
                                array=blankets;
                                break;
                        }

                        ArrayList<Blanket> list=new ArrayList<>();

                        if(!cbpismeni.isChecked() && !cbusmeni.isChecked())
                        {
                            list=array;
                        }
                        else {
                            for (Blanket b : array) {
                                if (b.pismeni == cbpismeni.isChecked() && b.usmeni == cbusmeni.isChecked())
                                    list.add(b);
                            }
                        }


                        if (list.size()!=0) {

                            activity.blanketSearch = list;
                            //Toast.makeText(SearchFragment.this.getContext(), "Uspesno", Toast.LENGTH_SHORT).show();
                            activity.setButton(true);
                            activity.setSearch();
                        }
                        else {
                            Toast.makeText(SearchFragment.this.getContext(), "Nema blanketa u bazi za zadati kriterijum", Toast.LENGTH_LONG).show();

                            btnsearch.setEnabled(true);

                            spinsmer.setEnabled(true);
                            spingodinast.setEnabled(true);
                            spinpredmet.setEnabled(true);
                            spinrok.setEnabled(true);
                            spingodina.setEnabled(true);

                            cbpismeni.setEnabled(true);
                            cbusmeni.setEnabled(true);

                            activity.setButton(true);

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d("Error: " + error.getMessage());
                Toast.makeText(SearchFragment.this.getContext(),error.toString(),Toast.LENGTH_SHORT).show();
                activity.setButton(true);

            }
        });

        RequestQueue queue = Volley.newRequestQueue(SearchFragment.this.getContext());
        queue.add(request);
    }

    enum IZABRANO {
        PREDMET,
        ROK,
        GODINA,
        PiR,
        PiG,
        RiG,
        PiRiG,
        NISTA
    }

    IZABRANO question(int predmet, int rok, String godina)
    {
        if(predmet!=-1&&rok!=-1&&!godina.equals("[Izaberite]"))
            return IZABRANO.PiRiG;
        else if(predmet!=-1 && rok!=-1)
            return IZABRANO.PiR;
        else if(predmet!=-1 &&!godina.equals("[Izaberite]"))
            return IZABRANO.PiG;
        else if(rok!=-1 && !godina.equals("[Izaberite]"))
            return IZABRANO.RiG;
        else if(predmet!=-1)
            return IZABRANO.PREDMET;
        else if(rok!=-1)
            return  IZABRANO.ROK;
        else if(!godina.equals("[Izaberite]"))
            return IZABRANO.GODINA;
        else
            return IZABRANO.NISTA;

    }

}
