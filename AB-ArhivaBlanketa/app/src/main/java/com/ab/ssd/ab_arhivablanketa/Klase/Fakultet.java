package com.ab.ssd.ab_arhivablanketa.Klase;


import java.io.Serializable;

public class Fakultet implements Serializable {
    public int ID;
    public String naziv;
    public String grad;

    public Fakultet(){}
    public Fakultet (int id, String n, String g)
    {
        this.ID=id;
        this.naziv=n;
        this.grad=g;
    }

    public String toString()
    {
        return naziv+", "+grad;
    }
}
