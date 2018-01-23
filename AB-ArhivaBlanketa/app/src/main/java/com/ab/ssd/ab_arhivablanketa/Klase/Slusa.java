package com.ab.ssd.ab_arhivablanketa.Klase;


import java.io.Serializable;

public class Slusa implements Serializable {

    public Korisnik korisnik;
    public Predmet predmet;

    public Slusa(){}
    public Slusa(Korisnik k, Predmet p)
    {
        this.korisnik=k;
        this.predmet=p;
    }
}
