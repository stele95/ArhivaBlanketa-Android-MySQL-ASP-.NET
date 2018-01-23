package com.ab.ssd.ab_arhivablanketa.Klase;


import java.io.Serializable;
import java.sql.Timestamp;

public class Komentar implements Serializable {

    public int ID;
    public Blanket blanket ;
    public Resenje resenje;
    public String datum ;
    public Korisnik dodao ;
    public boolean pripadaBlanketu;
    public String komentarData ;

    public Komentar(){}
    public Komentar(int i, Blanket b, Resenje r, String t, Korisnik k, boolean prip, String kom)
    {
        this.ID=i;
        this.blanket=b;
        this.resenje=r;
        this.datum=t;
        this.dodao=k;
        this.pripadaBlanketu=prip;
        this.komentarData=kom;
    }
}
