package com.ab.ssd.ab_arhivablanketa;


import android.util.Log;
import android.widget.Toast;

import com.ab.ssd.ab_arhivablanketa.Klase.Fakultet;

import org.json.JSONObject;

public class KorisnikInstance {
    protected int ID;
    protected String ime;
    protected String prezime;
    protected int brIndexa;
    protected String username;
    protected String email;
    protected String pass;
    protected Fakultet fakultet;
    protected boolean admin;

    protected JSONObject korisnikJSON;

    private static KorisnikInstance instance;

    public static KorisnikInstance getInstance(){return  instance;}
    public static void setInstance(KorisnikInstance k){instance=k;}

    public KorisnikInstance(){}
    public KorisnikInstance(int id, String ime, String prezime, int brIndexa, String username, String email, String pass, Fakultet faks, boolean admin)
    {
        if(this.instance!=null)
            setInstance(null);
        this.instance=new KorisnikInstance();
        instance.ID=id;
        instance.ime=ime;
        instance.prezime=prezime;
        instance.brIndexa=brIndexa;
        instance.username=username;
        instance.email=email;
        instance.pass=pass;
        instance.fakultet=faks;
        instance.admin=admin;

        instance.korisnikJSON=new JSONObject();
        JSONObject fax=new JSONObject();
        try {
            fax.put("IdFakultet",faks.ID);
            fax.put("Naziv",faks.naziv);
            fax.put("Grad",faks.grad);

            instance.korisnikJSON.put("IdKorisnik", id);
            instance.korisnikJSON.put("Ime", ime);
            instance.korisnikJSON.put("Prezime", prezime);
            instance.korisnikJSON.put("BrIndeksa", brIndexa);
            instance.korisnikJSON.put("Username", username);
            instance.korisnikJSON.put("Password", pass);
            instance.korisnikJSON.put("Email", email);
            instance.korisnikJSON.put("Fakultet", fax);
            instance.korisnikJSON.put("Admin",admin);
        }
        catch (Exception e)
        {

        }
    }
}
