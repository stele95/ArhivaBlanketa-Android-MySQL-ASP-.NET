package com.ab.ssd.ab_arhivablanketa.Klase;


import java.io.Serializable;

public class Rok implements Serializable {
    public int ID;
    public String naziv;

    public Rok(){}
    public Rok(int i, String n)
    {
        this.ID=i;
        this.naziv=n;
    }

    public String toString()
    {
        return naziv;
    }
}
