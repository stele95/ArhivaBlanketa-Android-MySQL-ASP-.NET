package com.ab.ssd.ab_arhivablanketa.Klase;


import java.io.Serializable;

public class GodinaStudija implements Serializable {
    public int ID ;
    public String godina ;

    public GodinaStudija(){}
    public GodinaStudija(int i, String n)
    {
        this.ID=i;
        this.godina=n;
    }

    public String toString() {
        return godina;
    }
}
