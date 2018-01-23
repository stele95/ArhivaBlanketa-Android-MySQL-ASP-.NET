package com.ab.ssd.ab_arhivablanketa.Klase;


import java.io.Serializable;

public class Predmet implements Serializable {
    public int ID ;
    public String naziv ;
    public Smer smer ;
    public GodinaStudija godinaStudija;

    public Predmet(){}
    public Predmet(int i, String n, Smer s, GodinaStudija g)
    {
        this.ID=i;
        this.naziv=n;
        this.smer=s;
        this.godinaStudija=g;
    }

    public String toString()
    {
        return naziv;
    }
}
