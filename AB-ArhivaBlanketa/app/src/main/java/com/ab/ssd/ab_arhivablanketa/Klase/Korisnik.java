package com.ab.ssd.ab_arhivablanketa.Klase;


import java.io.Serializable;

public class Korisnik implements Serializable {
    public int ID;
    public String ime;
    public String prezime;
    public int brIndexa;
    public String username;
    public String email;
    public String pass;
    public Fakultet fakultet;
    public boolean admin;

    public Korisnik(){}
    public Korisnik(int id, String ime, String prezime, int brIndexa, String username, String email, String pass, Fakultet faks, boolean admin)
    {
        this.ID=id;
        this.ime=ime;
        this.prezime=prezime;
        this.brIndexa=brIndexa;
        this.username=username;
        this.email=email;
        this.pass=pass;
        this.fakultet=faks;
        this.admin=admin;
    }
}


