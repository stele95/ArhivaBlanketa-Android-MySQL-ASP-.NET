package com.ab.ssd.ab_arhivablanketa.Klase;


import java.io.Serializable;

public class Smer implements Serializable {

    public int ID;
    public String naziv;
    public Fakultet fakultet ;

    public Smer(){}
    public Smer(int i, String s, Fakultet f)
    {
        this.ID=i;
        this.naziv=s;
        this.fakultet=f;
    }

    public String toString()
    {
        return naziv;
    }
}
